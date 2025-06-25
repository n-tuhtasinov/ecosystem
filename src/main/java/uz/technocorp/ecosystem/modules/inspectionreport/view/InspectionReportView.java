package uz.technocorp.ecosystem.modules.inspectionreport.view;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
public interface InspectionReportView {

    UUID getId();
    String getExecutionFilePath();
    LocalDate getFileUploadDate();
    String getRejectedCause();
    String getAssignedTasks();
    String getStatus();
    UUID getReportExecutionId();
    UUID getInspectorId();
    String getInspectorName();
}
