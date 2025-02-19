package uz.technocorp.ecosystem.modules.applicationexecutionprocess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.applicationexecutionprocess.projection.AppealExecutionProcessProjection;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.02.2025
 * @since v1.0
 */
public interface AppealExecutionProcessRepository extends JpaRepository<AppealExecutionProcess, UUID> {

    @Query(value = """
            select cast(aep.id as varchar) as id,
            description,
            cast(appeal_id as varchar) as appealId,
            u.name as executorName
                        from appeal_execution_process aep
                        join users u on u.id = aep.created_by
            where appeal_id = :appealId
            """, nativeQuery = true)
    List<AppealExecutionProcessProjection> getAllByAppealId(UUID appealId);
}
