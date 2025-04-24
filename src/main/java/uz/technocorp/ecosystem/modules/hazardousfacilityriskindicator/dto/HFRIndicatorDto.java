package uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.dto;

import uz.technocorp.ecosystem.enums.RiskAssessmentIndicator;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public record HFRIndicatorDto(
        RiskAssessmentIndicator indicatorType,
        UUID hazardousFacilityId,
        String description,
        Long tin
) {
}
