package uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.04.2025
 * @since v1.0
 */
public record HFRAssessmentDto(Short tin, Integer sumScore, UUID hazardousFacilityId) {
}
