package uz.technocorp.ecosystem.modules.irs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;

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

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName
            from ionizing_radiation_source irs
            inner join assign_inspector_irs aii on irs.id = aii.irs_id
            join users u on aii.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where irs.region_id = :regionId
            and aii.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegionAndInterval(Pageable pageable, Integer regionId, Integer intervalId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName
            from ionizing_radiation_source irs
            inner join assign_inspector_irs aii on irs.id = aii.irs_id
            join users u on aii.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where irs.legal_tin = :legalTin
            and aii.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTinAndInterval(Pageable pageable, Long legalTin, Integer intervalId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName
            from ionizing_radiation_source irs
            inner join assign_inspector_irs aii on irs.id = aii.irs_id
            join users u on aii.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where irs.registry_number = :registryNumber
            and aii.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumberAndInterval(Pageable pageable, String registryNumber, Integer intervalId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName
            from ionizing_radiation_source irs
            left join assign_inspector_irs aii on irs.id = aii.irs_id
            where irs.region_id = :regionId
            and aii.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegion(Pageable pageable, Integer regionId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName
            from ionizing_radiation_source irs
            left join assign_inspector_irs aii on irs.id = aii.irs_id
            where irs.legal_tin = :legalTin
            and aii.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTin(Pageable pageable, Long legalTin);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName
            from ionizing_radiation_source irs
            left join assign_inspector_irs aii on irs.id = aii.irs_id
            where irs.registry_number = :registryNumber
            and aii.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumber(Pageable pageable, String registryNumber);
}
