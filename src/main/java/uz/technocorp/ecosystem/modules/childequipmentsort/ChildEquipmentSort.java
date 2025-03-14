package uz.technocorp.ecosystem.modules.childequipmentsort;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.childequipmenttype.ChildEquipmentType;

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
public class ChildEquipmentSort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ChildEquipmentType.class, optional = false)
    @JoinColumn(name = "child_equipment_type_id", insertable = false, updatable = false)
    private ChildEquipmentType type;

    @Column(nullable = false, name = "child_equipment_type_id")
    private Integer typeId;
}
