package uz.technocorp.ecosystem.modules.hf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
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

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            legal_name as legalName,
            email,
            ht.name as typeName,
            r.name as regionName,
            d.name as districtName
            from hazardous_facility hf
            join region r on hf.region_id = r.id
            join district d on hf.district_id = d.id
            join hf_type ht on hf.hf_type_id = ht.id
            where profile_id = :profileId
            """, nativeQuery = true)
    Page<HfPageView> getAllByProfileId(UUID profileId, Pageable pageable);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            legal_name as legalName,
            email,
            ht.name as typeName,
            r.name as regionName,
            d.name as districtName
            from hazardous_facility hf
            join region r on hf.region_id = r.id
            join district d on hf.district_id = d.id
            join hf_type ht on hf.hf_type_id = ht.id
            """, nativeQuery = true)
    Page<HfPageView> getAll(Pageable pageable);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            address,
            legal_name as legalName,
            email,
            ht.name as typeName,
            r.name as regionName,
            d.name as districtName
            from hazardous_facility hf
            join region r on hf.region_id = r.id
            join district d on hf.district_id = d.id
            join hf_type ht on hf.hf_type_id = ht.id
            where hf.region_id = :regionId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegion(Pageable pageable, Integer regionId);
}
