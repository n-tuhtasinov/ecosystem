package uz.technocorp.ecosystem.modules.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.02.2025
 * @since v1.0
 */
public interface DocumentRepository extends JpaRepository<Document, UUID> {

    @Query(value = """
            select cast(id as varchar) as id,
                name,
                path,
                agreed
                from document
                where appeal_id = :appealId
            """, nativeQuery = true)
    List<DocumentProjection> getByAppealId(UUID appealId);
}
