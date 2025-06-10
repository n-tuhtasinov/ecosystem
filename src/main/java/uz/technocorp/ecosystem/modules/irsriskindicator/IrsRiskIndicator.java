package uz.technocorp.ecosystem.modules.irsriskindicator;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskassessment.enums.RiskAssessmentIndicator;
import uz.technocorp.ecosystem.shared.BaseEntity;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSource;

import java.time.LocalDate;
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
public class IrsRiskIndicator extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RiskAssessmentIndicator indicatorType;

    private Integer score;

    private LocalDate cancelledDate;

    private Integer scoreValue;

    private String filePath;

    private LocalDate fileDate;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = IonizingRadiationSource.class)
    @JoinColumn(name = "ionizing_radiation_source_id", insertable = false, updatable = false)
    private IonizingRadiationSource ionizingRadiationSource;

    @Column(name = "ionizing_radiation_source_id")
    private UUID ionizingRadiationSourceId;

    @Column(columnDefinition = "text")
    private String description;

    private Long tin;

    @ManyToOne(fetch = FetchType.LAZY)
    private RiskAnalysisInterval riskAnalysisInterval;

}
