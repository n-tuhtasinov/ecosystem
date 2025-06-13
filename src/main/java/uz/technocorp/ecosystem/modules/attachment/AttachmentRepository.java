package uz.technocorp.ecosystem.modules.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

    Optional<Attachment> findByPath(String path);

    void deleteByPath(String path);

    @Modifying
    @Transactional
    @Query("delete from Attachment a where a.path IN :paths")
    void deleteByPaths(@Param("paths") List<String> paths);
}
