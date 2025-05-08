package uz.technocorp.ecosystem.modules.childequipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public interface ChildEquipmentRepository extends JpaRepository<ChildEquipment, Integer> {

    List<ChildEquipment> findByEquipmentType(EquipmentType type);


    @Query("""
            select c from ChildEquipment c
            where (:type is null or c.equipmentType = :type)
            """)
    Page<ChildEquipment> findAllByEquipmentType(Pageable pageable, EquipmentType type);
}
