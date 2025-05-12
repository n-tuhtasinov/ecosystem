package uz.technocorp.ecosystem.modules.prevention;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Repository
public interface PreventionRepository extends JpaRepository<Prevention, UUID>, JpaSpecificationExecutor<Prevention> {


}
