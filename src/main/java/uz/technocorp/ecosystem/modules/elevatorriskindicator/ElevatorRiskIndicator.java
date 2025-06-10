package uz.technocorp.ecosystem.modules.elevatorriskindicator;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.shared.BaseEntity;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskassessment.enums.RiskAssessmentIndicator;

import java.time.LocalDate;
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
public class ElevatorRiskIndicator extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RiskAssessmentIndicator indicatorType;

    private Integer score;

    private LocalDate cancelledDate;

    private Integer scoreValue;

    private String filePath;

    private LocalDate fileDate;

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
