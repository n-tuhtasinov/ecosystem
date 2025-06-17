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

    @Query("select irs from IonizingRadiationSource irs where irs.id = :irsId")
    Optional<IonizingRadiationSource> getIrsById(UUID irsId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName,
            aii.id as assignId
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
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName,
            aii.id as assignId
            from ionizing_radiation_source irs
            inner join assign_inspector_irs aii on irs.id = aii.irs_id
            join users u on aii.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where aii.inspector_id = :inspectorId
            and aii.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByInspectorIdAndInterval(Pageable pageable, UUID inspectorId, Integer intervalId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName,
            aii.id as assignId
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
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName,
            aii.id as assignId
            from ionizing_radiation_source irs
            inner join assign_inspector_irs aii on irs.id = aii.irs_id
            join users u on aii.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where irs.legal_tin = :legalTin
            and aii.interval_id = :intervalId
            and aii.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTinAndIntervalAndInspectorId(Pageable pageable, Long legalTin, Integer intervalId, UUID inspectorId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName,
            aii.id as assignId
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
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName,
            p.full_name as inspectorName,
            aii.id as assignId
            from ionizing_radiation_source irs
            inner join assign_inspector_irs aii on irs.id = aii.irs_id
            join users u on aii.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where irs.registry_number = :registryNumber
            and aii.interval_id = :intervalId
            and aii.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumberAndIntervalAndInspectorId(Pageable pageable, String registryNumber, Integer intervalId, UUID inspectorId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName
            from ionizing_radiation_source irs
            left join assign_inspector_irs aii on irs.id = aii.irs_id
            where irs.region_id = :regionId
            and aii.interval_id = :intervalId
            and aii.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegion(Pageable pageable, Integer regionId, Integer intervalId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName
            from ionizing_radiation_source irs
            left join assign_inspector_irs aii on irs.id = aii.irs_id
            where irs.legal_tin = :legalTin
            and aii.interval_id = :intervalId
            and aii.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTin(Pageable pageable, Long legalTin, Integer intervalId);

    @Query(value = """
            select irs.id as id,
            registry_number as registryNumber,
            'INM' as name,
            legal_tin as legalTin,
            address,
            irs.legal_name as legalName
            from ionizing_radiation_source irs
            left join assign_inspector_irs aii on irs.id = aii.irs_id
            where irs.registry_number = :registryNumber
            and aii.interval_id = :intervalId
            and aii.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumber(Pageable pageable, String registryNumber, Integer intervalId);
}
