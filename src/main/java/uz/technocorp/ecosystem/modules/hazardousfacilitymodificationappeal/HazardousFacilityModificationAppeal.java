package uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.03.2025
 * @since v1.0
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HazardousFacilityModificationAppeal extends AuditEntity {

    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    private AppealType appealType;

    @Column(nullable = false)
    private String registryObjectNumber;

    private String reason;

    private String statement;

    private String actPath;

    private String appealPath;

    @Column(nullable = false)
    private Long orderNumber;

    private String number;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacility.class)
    @JoinColumn(name = "hazardous_facility_id", insertable = false, updatable = false)
    private HazardousFacility hazardousFacility;

    @Column(nullable = false, name = "hazardous_facility_id")
    private UUID hazardousFacilityId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;
}
