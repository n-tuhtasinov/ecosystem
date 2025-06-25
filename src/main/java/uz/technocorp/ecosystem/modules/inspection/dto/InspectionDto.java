package uz.technocorp.ecosystem.modules.inspection.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 23.05.2025
 * @since v1.0
 */
public record InspectionDto(LocalDate startDate, LocalDate endDate, Long tin, List<UUID> inspectorIdList, Integer intervalId, String decreePath) {
}
