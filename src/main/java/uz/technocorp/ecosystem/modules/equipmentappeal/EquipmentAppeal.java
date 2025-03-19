package uz.technocorp.ecosystem.modules.equipmentappeal;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.usertype.UserType;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.childequipmentsort.ChildEquipmentSort;
import uz.technocorp.ecosystem.modules.childequipmenttype.ChildEquipmentType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;
import uz.technocorp.ecosystem.modules.equipment.enums.Sphere;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 12.03.2025
 * @since v1.0
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentAppeal extends AuditEntity {

    @Enumerated(EnumType.STRING)
    private AppealType appealType;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Long orderNumber;

    @Column(nullable = false)
    private Long legalTin;

    @Enumerated(EnumType.STRING)
    private AppealStatus status;

    private LocalDate deadline;


//    @Column(columnDefinition = "jsonb", nullable = false)
//    private JsonNode data;

}
