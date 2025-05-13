package uz.technocorp.ecosystem.modules.hf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hf.view.HfSelectView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
public interface HazardousFacilityRepository extends JpaRepository<HazardousFacility, UUID> {

    @Query("SELECT h.orderNumber FROM HazardousFacility h ORDER BY h.orderNumber DESC LIMIT 1")
    Optional<Long> findMaxOrderNumber();

    @Query(value = """
            select cast(id as varchar) as id,
            registry_number as registryNumber,
            name
            from hazardous_facility
            """, nativeQuery = true)
    List<HfSelectView> findAllByProfileId(UUID profileId);
}
