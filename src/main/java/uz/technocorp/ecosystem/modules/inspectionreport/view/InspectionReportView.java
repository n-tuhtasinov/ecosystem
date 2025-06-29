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
    String getDefect();
    LocalDate getDate();
    LocalDate getDeadline();
    Boolean getEliminated();
    UUID getInspectionId();
    UUID getInspectorId();
    String getInspectorName();

}
