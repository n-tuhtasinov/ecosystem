package uz.technocorp.ecosystem.modules.hf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.hf.view.HfSelectView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
public interface HazardousFacilityRepository extends JpaRepository<HazardousFacility, UUID>, HfRepo {

    @Query("SELECT h.orderNumber FROM HazardousFacility h ORDER BY h.orderNumber DESC LIMIT 1")
    Optional<Long> findMaxOrderNumber();

    @Query(value = """
            select cast(id as varchar) as id,
            registry_number as registryNumber,
            name
            from hazardous_facility
            where profile_id = :profileId
            """, nativeQuery = true)
    List<HfSelectView> findAllByProfileId(UUID profileId);


    @Query("""
            select hf from HazardousFacility hf
                        left join fetch hf.hfType
            where hf.id = :hfId
            """)
    Optional<HazardousFacility> getHfById(UUID hfId);




//    @Query(value = """
//            select hf.id as id,
//            registry_number as registryNumber,
//            hf.name as name,
//            legal_tin as legalTin,
//            address,
//            legal_name as legalName,
//            email,
//            ht.name as typeName,
//            r.name as regionName,
//            d.name as districtName
//            from hazardous_facility hf
//            join region r on hf.region_id = r.id
//            join district d on hf.district_id = d.id
//            join hf_type ht on hf.hf_type_id = ht.id
//            where profile_id = :profileId
//            """, nativeQuery = true)
//    Page<HfPageView> getAllByProfileId(UUID profileId, Pageable pageable);
    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName,
            p.full_name as inspectorName,
            aih.id as assignId
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where hf.region_id = :regionId
            and aih.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegionAndInterval(Pageable pageable, Integer regionId, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName,
            p.full_name as inspectorName,
            aih.id as assignId
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where aih.inspector_id = :inspectorId
            and aih.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByInspectorIdAndInterval(Pageable pageable, UUID inspectorId, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName,
            p.full_name as inspectorName,
            aih.id as assignId
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where hf.legal_tin = :legalTin
            and aih.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTinAndInterval(Pageable pageable, Long legalTin, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName,
            p.full_name as inspectorName,
            aih.id as assignId
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where hf.legal_tin = :legalTin
            and aih.interval_id = :intervalId
            and aih.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTinAndIntervalAndInspectorId(Pageable pageable, Long legalTin, Integer intervalId, UUID inspectorId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName,
            p.full_name as inspectorName,
            aih.id as assignId
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where hf.registry_number = :registryNumber
            and aih.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumberAndInterval(Pageable pageable, String registryNumber, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName,
            p.full_name as inspectorName,
            aih.id as assignId
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where hf.registry_number = :registryNumber
            and aih.interval_id = :intervalId
            and aih.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumberAndIntervalAndInspectorId(Pageable pageable, String registryNumber, Integer intervalId, UUID inspectorId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName
            from hazardous_facility hf
            left join assign_inspector_hf aih on hf.id = aih.hf_id and aih.interval_id = :intervalId
            where hf.region_id = :regionId
            and aih.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegion(Pageable pageable, Integer regionId, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName
            from hazardous_facility hf
            left join assign_inspector_hf aih on hf.id = aih.hf_id and aih.interval_id = :intervalId
            where hf.legal_tin = :legalTin
            and aih.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTin(Pageable pageable, Long legalTin, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            hf.legal_name as legalName
            from hazardous_facility hf
            left join assign_inspector_hf aih on hf.id = aih.hf_id and aih.interval_id = :intervalId
            where hf.registry_number = :registryNumber
            and aih.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumber(Pageable pageable, String registryNumber, Integer intervalId);

    @Query(value = """
            select distinct(region_id)
                from hazardous_facility
                where legal_tin = :tin
            """, nativeQuery = true)
    Set<Integer> getAllRegionIdByLegalTin(Long tin);

    Optional<HazardousFacility> findByIdAndProfileId(UUID id, UUID profileId);

    Optional<HazardousFacility> findByRegistryNumber(String hfRegistryNumber);
}
