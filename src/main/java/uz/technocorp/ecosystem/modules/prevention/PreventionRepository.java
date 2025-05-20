package uz.technocorp.ecosystem.modules.prevention;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Repository
public interface PreventionRepository extends JpaRepository<Prevention, UUID>, JpaSpecificationExecutor<Prevention> {

    List<Prevention> findAllByProfileTinOrderByYearDesc(Long tin);

    Optional<Prevention> findByIdAndProfileTin(UUID preventionId, Long tin);

    Optional<Prevention> findByIdAndCreatedBy(UUID preventionId, UUID id);

    Optional<Prevention> findByIdAndOfficeId(UUID preventionId, Integer officeId);

    Optional<Prevention> findByProfileTinAndYear(Long tin, Integer year);

    @Query("select p from Prevention p where p.id = :preventionId and p.createdBy = :inspectorId and p.viewed is true and p.createdAt > :limitDate")
    Optional<Prevention> getForDelete(UUID preventionId, UUID inspectorId, LocalDateTime limitDate);

    @Modifying
    @Transactional
    @Query("update Prevention p set p.viewed = true, p.viewDate = :now where p.id = :preventionId")
    void updateView(UUID preventionId, LocalDateTime now);
}
