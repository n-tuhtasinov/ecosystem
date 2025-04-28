package uz.technocorp.ecosystem.modules.childequipment;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

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
public class ChildEquipment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;
}
