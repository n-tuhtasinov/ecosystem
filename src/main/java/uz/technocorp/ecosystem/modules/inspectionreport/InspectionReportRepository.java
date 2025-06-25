package uz.technocorp.ecosystem.modules.inspectionreport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
                assigned_tasks as assignedTasks,
                ire.file_upload_date as fileUploadDate,
                ire.execution_file_path as executionFilePath,
                ire.rejected_cause as rejectedCause,
                status,
                ire.id as reportExecutionId,
                ir.created_by as inspectorId
                from inspection_report ir
                left join inspection_report_execution ire on ir.id = ire.report_id
                where ir.inspection_id = :inspectionId
            """, nativeQuery = true)
    List<InspectionReportView> findAlByInspectionId(UUID inspectionId);
}
