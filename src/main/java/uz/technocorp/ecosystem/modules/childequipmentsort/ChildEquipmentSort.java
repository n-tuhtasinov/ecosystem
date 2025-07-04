package uz.technocorp.ecosystem.modules.childequipmentsort;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.shared.AuditEntity;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipment;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 13.03.2025
 * @since v1.0
 * @description This is sub child equipment type for only rides (attractions)
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildEquipmentSort extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ChildEquipment.class, optional = false)
    @JoinColumn(name = "child_equipment_id", insertable = false, updatable = false)
    private ChildEquipment childEquipment;

    @Column(nullable = false, name = "child_equipment_id")
    private Integer childEquipmentId;
}
