package uz.technocorp.ecosystem.modules.hazardousfacility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
public interface HazardousFacilityRepository extends JpaRepository<HazardousFacility, UUID> {

    @Query("SELECT h.serialNumber FROM HazardousFacility h ORDER BY h.serialNumber DESC LIMIT 1")
    Optional<Long> findMaxSerialNumber();
}
