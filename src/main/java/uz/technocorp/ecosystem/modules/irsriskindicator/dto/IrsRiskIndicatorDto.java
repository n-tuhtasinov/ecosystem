package uz.technocorp.ecosystem.modules.irsriskindicator.dto;

import uz.technocorp.ecosystem.modules.riskassessment.enums.RiskAssessmentIndicator;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public record IrsRiskIndicatorDto(
        RiskAssessmentIndicator indicatorType,
        UUID irsId,
        String description,
        Long tin,
        Integer intervalId
) {
}
