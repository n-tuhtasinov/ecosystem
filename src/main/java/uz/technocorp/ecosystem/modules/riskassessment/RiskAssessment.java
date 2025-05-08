package uz.technocorp.ecosystem.modules.riskassessment;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.shared.BaseEntity;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSource;

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
public class RiskAssessment extends BaseEntity {

    private short tin;

    private int sumScore;

    @Column(nullable = false)
    private String objectName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacility.class)
    @JoinColumn(name = "hazardous_facility_id", insertable = false, updatable = false)
    private HazardousFacility hazardousFacility;

    @Column(name = "hazardous_facility_id")
    private UUID hazardousFacilityId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = IonizingRadiationSource.class)
    @JoinColumn(name = "ionizing_radiation_source_id", insertable = false, updatable = false)
    private IonizingRadiationSource ionizingRadiationSource;

    @Column(name = "ionizing_radiation_source_id")
    private UUID ionizingRadiationSourceId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id", insertable = false, updatable = false)
    private Equipment equipment;

    @Column(name = "equipment_id")
    private UUID equipmentId;
}
