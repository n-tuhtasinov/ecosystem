package uz.technocorp.ecosystem.modules.attractionriskindicator;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditAndIdEntity;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskassessment.enums.RiskAssessmentIndicator;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttractionRiskIndicator extends AuditAndIdEntity {

    @Enumerated(EnumType.STRING)
    private RiskAssessmentIndicator indicatorType;

    private Integer score;

    @Column(columnDefinition = "text")
    private String description;

    private Long tin;

    @ManyToOne(fetch = FetchType.LAZY)
    private RiskAnalysisInterval riskAnalysisInterval;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id", insertable = false, updatable = false)
    private Equipment equipment;

    @Column(name = "equipment_id")
    private UUID equipmentId;
}
