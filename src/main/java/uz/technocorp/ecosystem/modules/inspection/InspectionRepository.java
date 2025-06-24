package uz.technocorp.ecosystem.modules.inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionShortInfo;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.05.2025
 * @since v1.0
 */
public interface InspectionRepository extends JpaRepository<Inspection, UUID>, InspectionRepo {


    @Query(value = """
            select *
            from inspection
            where interval_id = :intervalId
            and tin = :tin
            """, nativeQuery=true)
    Optional<Inspection> findAllByTinAndIntervalId(Long tin, Integer intervalId);

    @Query(value = """
            select i.id as id,
                start_date as startDate,
                end_date as endDate,
                status,
                program_path as programPath,
                special_code as specialCode,
                schedule_path as schedulePath,
                notification_letter_date as notificationLetterDate,
                notification_letter_path as notificationLetterPath,
                order_path as orderPath,
                measures_path as measuresPath,
                result_path as resultPath,
                json_agg(json_build_object('id', u.id, 'name', p.full_name)) as inspectors
                from inspection i
                join inspection_inspector ii on i.id = ii.inspection_id
                join users u on ii.inspector_id = u.id
                join profile p on u.profile_id = p.id
                where i.id = :id
                group by i.id, start_date, end_date, program_path, special_code, status,
                        schedule_path, notification_letter_date, notification_letter_path,
                        order_path, measures_path, result_path
            """, nativeQuery=true)
    Optional<InspectionView> getInspectionById(UUID id);

    @Query(value = """
            select distinct on (i.id) i.id            as id,
                                      i.start_date    as startDate,
                                      i.end_date      as endDate,
                                      i.tin           as tin,
                                      p.legal_name    as legalName,
                                      p.legal_address as legalAddress,
                                      i.special_code  as specialCode
            from (select i.*
                from inspection i
                join inspection_inspector ii on i.id = ii.inspection_id
                and i.status = :status and ii.inspector_id = :inspectorId
                where i.end_date between :startDate and :endDate
                union all
                select i.*
                from inspection i
                join inspection_inspector ii on i.id = ii.inspection_id
                and i.status = :status and ii.inspector_id = :inspectorId
                where i.start_date between :startDate and :endDate) i
                join profile p on i.tin = p.tin
            order by i.id, i.start_date
            """, nativeQuery=true)
    List<InspectionShortInfo> getAllByInspectorId(UUID inspectorId, LocalDate startDate, LocalDate endDate, String status);
}
