package uz.technocorp.ecosystem.modules.irs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 22.04.2025
 * @since v1.0
 */
public interface IonizingRadiationSourceRepository extends JpaRepository<IonizingRadiationSource, UUID>, IonizingRadiationSourceRepo {
    @Query("SELECT i.orderNumber FROM IonizingRadiationSource i ORDER BY i.orderNumber DESC LIMIT 1")
    Optional<Long> findMaxOrderNumber();

    @Query(value = """
            select distinct(region_id)
                from ionizing_radiation_source
                where legal_tin = :tin
            """, nativeQuery = true)
    Set<Integer> getAllRegionIdByLegalTin(Long tin);

    @Query("select irs from IonizingRadiationSource irs where irs.id = :irsId")
    Optional<IonizingRadiationSource> getIrsById(UUID irsId);
}
