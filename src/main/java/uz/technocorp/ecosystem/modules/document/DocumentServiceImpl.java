package uz.technocorp.ecosystem.modules.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.dto.Signer;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;
import uz.technocorp.ecosystem.modules.eimzo.EImzoProxy;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7VerifyAttachedJson;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final EImzoProxy eImzoProxy;
    private final DocumentRepository documentRepository;
    private final AttachmentService attachmentService;

    @Override
    public void create(DocumentDto dto) {
        Signer signer = new Signer(getSigner(dto.sign(), dto.ip()), dto.executedBy(), LocalDateTime.now());

        documentRepository.save(Document.builder()
                .path(dto.path())
                .signedContent(dto.sign())
                .appealId(dto.belongId())
                .signers(List.of(signer)) // TODO Agar bir nechta user imzolasa signers listni to'g'irlash kerak. Update documentni qoshish kerak
                .documentType(dto.documentType())
                .build());

        // Delete attachment without the file
        attachmentService.deleteByPath(dto.path());
    }

    @Override
    public List<DocumentProjection> findByAppealId(UUID appealId) {
        return documentRepository.getByAppealId(appealId);
    }

    @Override
    public void delete(UUID id) {
        documentRepository.deleteById(id);
    }

    private String getSigner(String sign, String ip) {
        Pkcs7VerifyAttachedJson pkcs7VerifyAttached = eImzoProxy.pkcs7Attached(AppConstants.HOST, ip, sign);

        if (!"1".equals(pkcs7VerifyAttached.getStatus())) {
            throw new ResourceNotFoundException("Document verification failed");
        }

        String subjectName = pkcs7VerifyAttached.getPkcs7Info().getSigners().getLast().getCertificate().getFirst().getSubjectName();
        return subjectName.split(",")[0].replace("CN=", "").trim();
    }
}
