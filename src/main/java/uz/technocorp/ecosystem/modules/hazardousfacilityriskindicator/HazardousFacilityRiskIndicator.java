package uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.enums.HazardousFacilityRiskIndicatorType;

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
public class HazardousFacilityRiskIndicator extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private HazardousFacilityRiskIndicatorType indicatorType;

    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacility.class)
    @JoinColumn(name = "hazardous_facility_id", insertable = false, updatable = false)
    private HazardousFacility hazardousFacility;

    @Column(name = "hazardous_facility_id")
    private UUID hazardousFacilityId;

    @Column(columnDefinition = "text")
    private String description;

    private Long tin;

}
