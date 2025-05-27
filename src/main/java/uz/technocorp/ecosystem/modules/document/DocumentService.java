package uz.technocorp.ecosystem.modules.document;

import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByReply;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByRequest;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public interface DocumentService {

    void create(DocumentDto documentDto);

    void delete(UUID id);

    DocumentViewByReply getById(UUID documentId);

    List<DocumentViewByRequest> getRequestDocumentsByAppealId(UUID appealId);

    List<DocumentViewByReply> getReplyDocumentsByAppealId(User user, UUID appealId);

}
