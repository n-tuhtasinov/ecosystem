package uz.technocorp.ecosystem.modules.elevatorriskindicator.dto;

import uz.technocorp.ecosystem.modules.riskassessment.enums.RiskAssessmentIndicator;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public record EquipmentRiskIndicatorDto(
        RiskAssessmentIndicator indicatorType,
        UUID equipmentId,
        String description,
        Long tin
) {
}
