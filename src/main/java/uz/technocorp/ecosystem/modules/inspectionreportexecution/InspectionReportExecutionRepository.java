package uz.technocorp.ecosystem.modules.inspectionreportexecution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
public interface InspectionReportExecutionRepository extends JpaRepository<InspectionReportExecution, UUID> {

    @Query(value = """
            select count(distinct r.id)
            from inspection_report_execution ire
            join inspection_report r on ire.report_id = r.id
            and r.inspection_id = :inspectionId
            and status <> 'ACCEPTED'
            """, nativeQuery = true)
    Integer getCountNotAcceptedReports(UUID inspectionId);
}
