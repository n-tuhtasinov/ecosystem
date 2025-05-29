package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealRepository extends JpaRepository<Appeal, UUID>, AppealRepo {

    @Query("select a.orderNumber from Appeal a order by a.orderNumber desc limit 1")
    Optional<Long> getMax();

    @Query(nativeQuery = true, value = """
            select a.id            as id,
                   a.appeal_type   as appealType,
                   a.number        as number,
                   a.legal_name    as legalName,
                   a.legal_tin     as legalTin,
                   a.address       as address,
                   a.phone_number  as phoneNumber,
                   a.deadline as deadline
            from appeal a
            where a.deadline between :startDate and :endDate
              and a.executor_id = :inspectorId
              and a.status = :appealStatus""")
    List<AppealViewByPeriod> getAllByPeriodAndInspectorId(LocalDate startDate, LocalDate endDate, UUID inspectorId, String appealStatus);


    Optional<AppealViewById> getAppealById(UUID id);

    Optional<Appeal> findByIdAndStatusAndExecutorId(UUID appealId, AppealStatus appealStatus, UUID inspectorId);

    @Modifying
    @Transactional
    @Query("update Appeal set conclusion = :conclusion, replyDocumentId = :replyDocumentId, status = :status  where id = :id")
    void changeStatusAndSetConclusionAndReplyId(UUID id, String conclusion, UUID replyDocumentId, AppealStatus status);

    @Modifying
    @Transactional
    @Query("update Appeal set appealDocumentId = :documentId where id = :appealId")
    void setDocumentId(UUID appealId, UUID documentId);
}
