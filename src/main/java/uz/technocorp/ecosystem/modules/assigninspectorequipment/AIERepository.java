package uz.technocorp.ecosystem.modules.assigninspectorequipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.assigninspectorequipment.dto.AssignInfoDto;
import uz.technocorp.ecosystem.modules.user.dto.InspectorDto;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
public interface AIERepository extends JpaRepository<AssignInspectorEquipment, UUID> {

    @Query(value = """
            select u.id as id,
                p.full_name as inspectorName,
                a.created_at as date,
                a.interval_id as intervalId
                from  assign_inspector_equipment a
                join users u on a.inspector_id = u.id
                join profile p on p.id = u.profile_id
                where a.id = :assignId
            """, nativeQuery = true)
    Optional<AssignInfoDto> findInfo(UUID assignId);
}
