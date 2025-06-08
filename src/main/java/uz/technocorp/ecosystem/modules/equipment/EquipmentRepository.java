package uz.technocorp.ecosystem.modules.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public interface EquipmentRepository extends JpaRepository<Equipment, UUID>, EquipmentRepo {

    @Query("select e.orderNumber from Equipment e where e.type = :equipmentType order by e.orderNumber desc limit 1")
    Optional<Long> getMax(EquipmentType equipmentType);

    @Query(value = """
            select distinct(region_id)
                from equipment
                where legal_tin = :tin
            """, nativeQuery = true)
    Set<Integer> getAllRegionIdByLegalTin(Long tin);
}
