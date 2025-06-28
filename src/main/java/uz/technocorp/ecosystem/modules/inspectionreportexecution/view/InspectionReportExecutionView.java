package uz.technocorp.ecosystem.modules.inspectionreportexecution.view;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 26.06.2025
 * @since v1.0
 */
public interface InspectionReportExecutionView {

    UUID getId();
    String getExecutionFilePath();
    LocalDate getFileUploadDate();
    String getRejectedCause();
    String getStatus();
}
