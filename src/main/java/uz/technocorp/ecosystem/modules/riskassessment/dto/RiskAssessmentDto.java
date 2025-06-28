package uz.technocorp.ecosystem.modules.riskassessment.dto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.04.2025
 * @since v1.0
 */

public interface RiskAssessmentDto {
    UUID getObjectId();
    Integer getSumScore();
    Long getTin();
}
