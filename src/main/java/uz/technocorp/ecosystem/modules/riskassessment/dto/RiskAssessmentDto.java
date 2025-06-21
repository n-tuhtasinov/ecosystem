package uz.technocorp.ecosystem.modules.riskassessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.04.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskAssessmentDto {
    private Long tin;
    private Integer sumScore;
    private UUID objectId;
}
