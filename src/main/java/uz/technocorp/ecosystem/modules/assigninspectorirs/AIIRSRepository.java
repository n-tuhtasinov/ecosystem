package uz.technocorp.ecosystem.modules.assigninspectorirs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.assigninspectorequipment.AssignInspectorInfo;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
public interface AIIRSRepository extends JpaRepository<AssignInspectorIrs, UUID> {

    @Query(value = """
            select u.id as id,
                p.full_name as inspectorName,
                a.interval_id as intervalId,
                a.created_at as date,
                i.start_date as startDate,
                i.end_date as endDate
                from  assign_inspector_irs a
                join users u on a.inspector_id = u.id
                join profile p on p.id = u.profile_id
                join risk_analysis_interval i on i.id = a.interval_id
                where a.id = :assignId
            """, nativeQuery = true)
    Optional<AssignInspectorInfo> findInfo(UUID assignId);

    Optional<AssignInspectorIrs> findByIrsIdAndIntervalId(UUID inspectorId, Integer intervalId);
}
