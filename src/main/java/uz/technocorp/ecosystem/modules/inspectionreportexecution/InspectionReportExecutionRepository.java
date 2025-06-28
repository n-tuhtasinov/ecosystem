package uz.technocorp.ecosystem.modules.inspectionreportexecution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.view.InspectionReportExecutionView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
public interface InspectionReportExecutionRepository extends JpaRepository<InspectionReportExecution, UUID> {

    @Query(value = """
            select id,
            execution_file_path as executionFilePath,
            file_upload_date as fileUploadDate,
            rejected_cause as rejectedCause,
            status
            from inspection_report_execution
            where report_id = :reportId
            """, nativeQuery = true)
    List<InspectionReportExecutionView> getAllByReportId(UUID reportId);
}
