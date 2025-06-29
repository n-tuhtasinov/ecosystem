package uz.technocorp.ecosystem.modules.inspectionreport.dto;

import java.time.LocalDate;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
public record InspectionReportDto(String defect, LocalDate deadline) {
}
