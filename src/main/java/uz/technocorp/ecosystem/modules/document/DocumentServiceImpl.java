package uz.technocorp.ecosystem.modules.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.dto.Signer;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByReply;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByRequest;
import uz.technocorp.ecosystem.modules.eimzo.EImzoProxy;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7VerifyAttachedJson;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;

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

    private final ObjectMapper objectMapper;
    @Value("${app.e-imzo.host}")
    private String host;

    private final EImzoProxy eImzoProxy;
    private final DocumentRepository repository;
    private final AttachmentService attachmentService;

    @Override
    public void create(DocumentDto dto) {
        Signer signer = new Signer(getSigner(dto.sign(), dto.ip()), dto.executedBy(), LocalDateTime.now());

        Document document = new Document();

        document.setBelongId(dto.belongId());
        document.setPath(dto.path());
        document.setSignedContent(dto.sign());
        document.setSigners(List.of(signer)); // TODO Agar bir nechta user imzolasa signers listni to'g'irlash kerak. Update documentni qoshish kerak
        document.setDocumentType(dto.documentType());
        document.setIsConfirmed(false);
        document.setDescription(null);

        repository.save(document);

        // Delete attachment without the file
        attachmentService.deleteByPath(dto.path());
    }


    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public DocumentViewByReply getById(UUID documentId) {
        return repository.getDocumentById(documentId).orElseThrow(() -> new ResourceNotFoundException("Document", "ID", documentId));
    }

    @Override
    public List<DocumentViewByRequest> getRequestDocumentsByAppealId(UUID appealId) {
        return repository.getRequestDocumentsByAppealId(appealId, DocumentType.APPEAL.name());
    }

    @Override
    public List<DocumentViewByReply> getReplyDocumentsByAppealId(User user, UUID appealId) {
        if (user.getRole().equals(Role.LEGAL) || user.getRole().equals(Role.INDIVIDUAL)) {
            return repository.getReplyDocumentsByAppealIdAndConfirmed(appealId, DocumentType.APPEAL.name(), true);
        }

        return repository.getReplyDocumentsByAppealIdAndConfirmed(appealId, DocumentType.APPEAL.name(), null);
    }

    public List<Signer> convertToList(String signers) {
        try {
            return objectMapper.readValue(signers, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSigner(String sign, String ip) {
        Pkcs7VerifyAttachedJson pkcs7VerifyAttached = eImzoProxy.pkcs7Attached(host, ip, sign);
        if (!"1".equals(pkcs7VerifyAttached.getStatus())) {
            throw new ResourceNotFoundException("Document verification failed");
        }
        String subjectName = pkcs7VerifyAttached.getPkcs7Info().getSigners().getLast().getCertificate().getFirst().getSubjectName();
        return subjectName.split(",")[0].replace("CN=", "").trim();
    }
}
