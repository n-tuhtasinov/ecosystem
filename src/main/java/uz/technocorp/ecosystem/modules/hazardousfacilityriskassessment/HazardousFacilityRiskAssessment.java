package uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.04.2025
 * @since v1.0
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HazardousFacilityRiskAssessment extends AuditEntity {

    private short tin;

    private int sumScore;

    @Column(nullable = false)
    private String objectName;
}
