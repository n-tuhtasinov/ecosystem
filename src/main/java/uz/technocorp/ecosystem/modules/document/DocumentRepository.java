package uz.technocorp.ecosystem.modules.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByReply;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.02.2025
 * @since v1.0
 */
public interface DocumentRepository extends JpaRepository<Document, UUID> {

    @Query(nativeQuery = true, value = """
             select id            as id,
                    document_type as documentType,
                    path          as path,
                    1 = 1         as isSigned
             from document
             where id = :documentId
            """)
    Optional<DocumentViewByReply> getDocumentById(UUID documentId);

    @Query(nativeQuery = true, value = """
            select id            as documentId,
                   document_type as documentType,
                   1 = 1         as isSigned,
                   path,
                   signers
            from document
            where appeal_id = :appealId
              and document_type = :documentType
            order by created_at desc
            """)
    List<DocumentViewByRequest> getRequestDocumentsByAppealId(UUID appealId, String documentType);

    @Query(nativeQuery = true, value = """
            select id            as documentId,
                   document_type as documentType,
                   1 = 1         as is_signed,
                   path,
                   is_confirmed  as isConfirmed,
                   description,
                   signers
            from document
            where appeal_id = :appealId
              and document_type != :documentType
              and (:isConfirmed is null or is_confirmed = :isConfirmed)
            order by created_at desc
            """)
    List<DocumentViewByReply> getReplyDocumentsByAppealIdAndConfirmed(UUID appealId, String documentType, Boolean isConfirmed);
}
