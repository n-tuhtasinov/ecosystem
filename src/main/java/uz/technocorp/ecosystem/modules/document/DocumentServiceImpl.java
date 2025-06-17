package uz.technocorp.ecosystem.modules.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.RejectDto;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.dto.Signer;
import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByReply;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByRequest;
import uz.technocorp.ecosystem.modules.eimzo.EImzoProxy;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7VerifyAttachedJson;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final UserRepository userRepository;
    @Value("${app.e-imzo.host}")
    private String host;

    private final EImzoProxy eImzoProxy;
    private final DocumentRepository repository;
    private final AttachmentService attachmentService;

    @Override
    public void create(DocumentDto dto) {

        List<Signer> signers = new ArrayList<>();

        for (UUID id : dto.executorIds()) {

            if (id == dto.singerId()) {
                String signerName = getSigner(dto.sign(), dto.ip());
                Signer signer = new Signer(signerName, id, LocalDateTime.now(), true);
                signers.add(signer);
                continue;
            }

            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));
            Signer signer = new Signer(user.getName(), id, null, false);
            signers.add(signer);
        }

        Document document = new Document(dto.belongId(), dto.path(), dto.sign(), signers, dto.documentType(), dto.status(), null, signers.size() == 1);

        repository.save(document);

        // Delete attachment without the file
        attachmentService.deleteByPath(dto.path());
    }


    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }


    @Override
    public List<DocumentViewByRequest> getRequestDocumentsByAppealId(UUID appealId) {
        return repository.getRequestDocumentsByAppealId(appealId, DocumentType.APPEAL.name());
    }

    @Override
    public List<DocumentViewByReply> getReplyDocumentsByAppealId(User user, UUID appealId) {
        if (user.getRole().equals(Role.LEGAL) || user.getRole().equals(Role.INDIVIDUAL)) {
            return repository.getReplyDocumentsByAppealIdAndAgreementStatus(appealId, DocumentType.APPEAL.name(), AgreementStatus.APPROVED.name());
        }

        return repository.getReplyDocumentsByAppealIdAndAgreementStatus(appealId, DocumentType.APPEAL.name(), null);
    }

    @Override
    public void reject(User user, RejectDto dto) {
        Document document = repository.findById(dto.documentId()).orElseThrow(() -> new ResourceNotFoundException("Document", "ID", dto.documentId()));
        document.setDescription(dto.description());

        Role role = user.getRole();
        if (role == Role.REGIONAL) {
            if (document.getAgreementStatus() != null) {
                throw new RuntimeException("Hujjat agreementStatusi avval o'zgartirilgan. Hozirgi holati: " + document.getAgreementStatus().name());
            }
            document.setAgreementStatus(AgreementStatus.NOT_AGREED);
        } else if (role == Role.MANAGER) {
            if (document.getAgreementStatus() != AgreementStatus.AGREED) {
                throw new RuntimeException("Hujjat agreementStatusi 'AGREED' holatida emas. Hozirgi holati: " + document.getAgreementStatus().name());
            }
            document.setAgreementStatus(AgreementStatus.NOT_APPROVED);
        } else {
            throw new RuntimeException(role.name() + " roli uchun hali logika yozilmagan. Backendchilarga ayting )))");
        }

        repository.save(document);
    }

    @Override
    public void confirmationByAppeal(User user, UUID documentId) {
        Document document = repository.findById(documentId).orElseThrow(() -> new ResourceNotFoundException("Document", "ID", documentId));

        Role role = user.getRole();
        if (role == Role.REGIONAL) {
            document.setAgreementStatus(AgreementStatus.AGREED);
        } else if (role == Role.MANAGER) {
            document.setAgreementStatus(AgreementStatus.APPROVED);
        } else {
            throw new RuntimeException(role.name() + " roli uchun hali logika yozilmagan. Backendchilarga ayting )))");
        }

        repository.save(document);
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
