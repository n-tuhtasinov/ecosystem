package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.enums.OwnerType;
import uz.technocorp.ecosystem.modules.appeal.repo.AppealRepo;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.statistics.view.AppealStatusCountView;

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
            select a.id             as id,
                   a.appeal_type    as appealType,
                   a.number         as number,
                   a.owner_name     as ownerName,
                   a.owner_identity as ownerIdentity,
                   a.address        as address,
                   a.phone_number   as phoneNumber,
                   a.deadline       as deadline
            from appeal a
            where a.deadline between :startDate and :endDate
              and a.executor_id = :inspectorId
              and a.status = :appealStatus""")
    List<AppealViewByPeriod> getAllByPeriodAndInspectorId(LocalDate startDate, LocalDate endDate, UUID inspectorId, String appealStatus);

    Optional<AppealViewById> getAppealById(UUID id);

    Optional<Appeal> findByIdAndStatusAndExecutorId(UUID appealId, AppealStatus appealStatus, UUID inspectorId);

    Optional<Appeal> findByIdAndStatus(UUID appealId, AppealStatus appealStatus);

    Optional<Appeal> findByIdAndStatusAndAppealType(UUID appealId, AppealStatus appealStatus, AppealType type);

    Optional<Appeal> findByIdAndStatusAndOfficeId(UUID appealId, AppealStatus appealStatus, Integer officeId);

    Optional<Appeal> findByIdAndStatusAndOfficeIdAndAppealType(UUID appealId, AppealStatus appealStatus, Integer officeId, AppealType type);

    List<AppealViewById> findAllByIdIn(List<UUID> appealIds);

    @Modifying
    @Transactional
    @Query("update Appeal set conclusion = :conclusion, status = :status  where id = :id")
    void changeStatusAndSetConclusion(UUID id, String conclusion, AppealStatus status);

    @Query(nativeQuery = true,
            value = """
                    select case when o.name is not null then o.name else 'Qo''mita' end as officeName,
                           count(a.id) as total,
                           count(case when a.status = 'IN_PROCESS' then 1 end) as inProcess,
                           count(case when a.status = 'IN_AGREEMENT' then 1 end) as inAgreement,
                           count(case when a.status = 'IN_APPROVAL' then 1 end) as inApproval,
                           count(case when a.status = 'COMPLETED' then 1 end) as completed,
                           count(case when a.status = 'REJECTED' then 1 end) as rejected,
                           count(case when a.status = 'CANCELED' then 1 end) as canceled
                    from appeal a
                             left join office o on a.office_id = o.id
                    where a.created_at >= :startDate
                      and a.owner_type = :ownerType
                      and (cast(:endDate as date) is null or a.created_at <= :endDate) group by o.name
                    """)
    List<AppealStatusCountView> countByAppealStatus(String ownerType, LocalDate startDate, LocalDate endDate);
}
