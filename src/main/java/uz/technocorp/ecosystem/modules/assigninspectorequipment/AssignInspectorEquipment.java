package uz.technocorp.ecosystem.modules.assigninspectorequipment;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class AssignInspectorEquipment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "inspector_id", insertable = false, updatable = false)
    private User inspector;

    @Column(name = "inspector_id")
    private UUID inspectorId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id", insertable = false, updatable = false)
    private Equipment equipment;

    @Column(name = "equipment_id")
    private UUID equipmentId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = RiskAnalysisInterval.class)
    @JoinColumn(name = "interval_id", insertable = false, updatable = false)
    private RiskAnalysisInterval interval;

    @Column(name = "interval_id")
    private Integer intervalId;
}
