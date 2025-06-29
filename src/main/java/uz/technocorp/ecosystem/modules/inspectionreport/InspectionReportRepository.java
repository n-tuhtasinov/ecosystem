package uz.technocorp.ecosystem.modules.inspectionreport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.inspectionreport.view.InspectionReportForAct;
import uz.technocorp.ecosystem.modules.inspectionreport.view.InspectionReportView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
public interface InspectionReportRepository extends JpaRepository<InspectionReport, UUID> {

    @Query(value = """
            select ir.id as id,
                defect,
                ir.created_at as date,
                ir.deadline as deadline,
                eliminated,
                inspection_id as inspectionId,
                u.id as inspectorId,
                p.full_name as inspectorName
                from inspection_report ir
                join users u on ir.created_by = u.id
                join profile p on u.profile_id = p.id
                where ir.inspection_id = :inspectionId
                and eliminated = :eliminated
            """, nativeQuery = true)
    Page<InspectionReportView> getAllByInspectionId(UUID inspectionId, Pageable pageable, boolean eliminated);

    @Query(value = """
            select ir.id as id,
                defect,
                ir.created_at as date,
                ir.deadline as deadline,
                eliminated,
                inspection_id as inspectionId,
                u.id as inspectorId,
                p.full_name as inspectorName
                from inspection_report ir
                join users u on u.id = :inspectorId and ir.created_by = u.id
                join profile p on u.profile_id = p.id
                where ir.inspection_id = :inspectionId
            """, nativeQuery = true)
    Page<InspectionReportView> getAllByInspectionIdAndInspectorId(UUID inspectorId, UUID inspectionId, Pageable pageable);

    @Query(value = """
            select count(distinct id)
            from inspection_report
            where inspection_id = :inspectionId
            and eliminated is not true
            """, nativeQuery = true)
    Integer getCountNotEliminatedReports(UUID inspectionId);

    @Query(value = """
            select id,
                defect,
                deadline as deadline
                from inspection_report
                where inspection_id = :inspectionId
            """, nativeQuery = true)
    List<InspectionReportForAct> getAllByInspectionId(UUID inspectionId);
}
