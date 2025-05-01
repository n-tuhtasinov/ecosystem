package uz.technocorp.ecosystem.modules.childequipment;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public interface ChildEquipmentRepository extends JpaRepository<ChildEquipment, Integer> {

    List<ChildEquipment> findByEquipmentType(EquipmentType type);
}
