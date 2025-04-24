package uz.technocorp.ecosystem.modules.irsriskindicator;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.enums.RiskAssessmentIndicator;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSource;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 14.04.2025
 * @since v1.0
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IrsRiskIndicator extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private RiskAssessmentIndicator indicatorType;

    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = IonizingRadiationSource.class)
    @JoinColumn(name = "ionizing_radiation_source_id", insertable = false, updatable = false)
    private IonizingRadiationSource ionizingRadiationSource;

    @Column(name = "ionizing_radiation_source_id")
    private UUID ionizingRadiationSourceId;

    @Column(columnDefinition = "text")
    private String description;

    private Long tin;

}
