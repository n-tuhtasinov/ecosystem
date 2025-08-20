package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.repo.AppealRepo;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealStatusView;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealTypeView;

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
                    with office_list as (select id, name
                                         from office
                                         union all
                                         select null as id, 'Qo''mita' as name)
                    select ol.name                                              as "officeName",
                           count(a.id)                                          as total,
                           count(a.id) filter (where a.status = 'IN_PROCESS')   as "inProcess",
                           count(a.id) filter (where a.status = 'IN_AGREEMENT') as "inAgreement",
                           count(a.id) filter (where a.status = 'IN_APPROVAL')  as "inApproval",
                           count(a.id) filter (where a.status = 'COMPLETED')    as "completed",
                           count(a.id) filter (where a.status = 'REJECTED')     as "rejected",
                           count(a.id) filter (where a.status = 'CANCELED')     as "canceled"
                    from office_list ol
                             left join
                         appeal a on (ol.id = a.office_id or (ol.id is null and a.office_id is null))
                             and a.owner_type = :ownerType
                             and a.created_at >= :startDate
                             and (cast(:endDate as timestamp) is null or a.created_at <= :endDate)
                    group by ol.name
                    """)
    List<StatByAppealStatusView> countByAppealStatus(String ownerType, LocalDate startDate, LocalDate endDate);

    @Query(nativeQuery = true, value = """
            select typesSource.appeal_type_name                    as appealType,
                   count(a.id)                                     as total,
                   count(a.id) filter ( where a.office_id is null) as committee,
                   count(a.id) filter ( where r.soato = 1735)      as karakalpakstan,
                   count(a.id) filter ( where r.soato = 1703)      as andijan,
                   count(a.id) filter ( where r.soato = 1706)      as bukhara,
                   count(a.id) filter ( where r.soato = 1730)      as fergana,
                   count(a.id) filter ( where r.soato = 1708)      as jizzakh,
                   count(a.id) filter ( where r.soato = 1710)      as kashkadarya,
                   count(a.id) filter ( where r.soato = 1733)      as khorezm,
                   count(a.id) filter ( where r.soato = 1714)      as namangan,
                   count(a.id) filter ( where r.soato = 1712)      as navoi,
                   count(a.id) filter ( where r.soato = 1718)      as samarkand,
                   count(a.id) filter ( where r.soato = 1724)      as syrdarya,
                   count(a.id) filter ( where r.soato = 1722)      as surkhandarya,
                   count(a.id) filter ( where r.soato = 1726)      as tashkent,
                   count(a.id) filter ( where r.soato = 1727)      as tashkentRegion
            from (select unnest(:appealTypes) as appeal_type_name) as typesSource
                     left join appeal a on typesSource.appeal_type_name = a.appeal_type
                            and a.owner_type = :ownerType
                            and a.created_at >= :startDate
                            and (cast(:endDate as timestamp) is null or a.created_at <= :endDate)
                     left join office o on a.office_id = o.id
                     left join region r on o.region_id = r.id
            group by typesSource.appeal_type_name
            """)
    List<StatByAppealTypeView> countByAppealType(String ownerType, LocalDate startDate, LocalDate endDate, String[] appealTypes);
}
