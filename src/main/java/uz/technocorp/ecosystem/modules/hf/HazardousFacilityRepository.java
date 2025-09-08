package uz.technocorp.ecosystem.modules.hf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hf.view.HfCountByStatusView;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.hf.view.HfSelectView;

import java.time.LocalDate;
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
public interface HazardousFacilityRepository extends JpaRepository<HazardousFacility, UUID>, HfRepo, JpaSpecificationExecutor<HazardousFacility> {

    @Query("SELECT h.orderNumber FROM HazardousFacility h where h.orderNumber is not null ORDER BY h.orderNumber DESC LIMIT 1")
    Optional<Long> findMaxOrderNumber();

    @Query(value = """
            select hf.id              as id,
                   hf.registry_number as registryNumber,
                   hf.name,
                   hf.region_id       as regionId,
                   hf.district_id     as districtId,
                   hf.address
            from hazardous_facility hf
            where (:registryNumber = '' or :registryNumber = hf.registry_number)
              and hf.legal_tin = :legalTin
            """, nativeQuery = true)
    List<HfSelectView> findAllByTinAndRegistryNumber(Long legalTin, String registryNumber);


    @Query("""
            select hf from HazardousFacility hf
                        left join fetch hf.hfType
            where hf.id = :hfId
            """)
    Optional<HazardousFacility> getHfById(UUID hfId);


    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            hf.address,
            hf.legal_name as legalName,
            p.name as inspectorName,
            aih.id as assignId,
            scores.total_score as score
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
            where hf.region_id = :regionId
            and aih.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegionAndInterval(Pageable pageable, Integer regionId, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            hf.address,
            hf.legal_name as legalName,
            p.name as inspectorName,
            aih.id as assignId,
            scores.total_score as score
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
            where aih.inspector_id = :inspectorId
            and aih.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByInspectorIdAndInterval(Pageable pageable, UUID inspectorId, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            hf.address,
            hf.legal_name as legalName,
            p.name as inspectorName,
            aih.id as assignId,
            scores.total_score as score
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
            where hf.legal_tin = :legalTin
            and aih.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTinAndInterval(Pageable pageable, Long legalTin, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            hf.address,
            hf.legal_name as legalName,
            p.name as inspectorName,
            aih.id as assignId,
            scores.total_score as score
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
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
            hf.address,
            hf.legal_name as legalName,
            p.name as inspectorName,
            aih.id as assignId,
            scores.total_score as score
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
            where hf.registry_number = :registryNumber
            and aih.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumberAndInterval(Pageable pageable, String registryNumber, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            hf.address,
            hf.legal_name as legalName,
            p.name as inspectorName,
            aih.id as assignId,
            scores.total_score as score
            from hazardous_facility hf
            inner join assign_inspector_hf aih on hf.id = aih.hf_id
            join users u on aih.inspector_id = u.id
            join profile p on u.profile_id = p.id
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
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
            hf.address,
            hf.legal_name as legalName,
            null as inspectorName,
            null as assignId,
            scores.total_score as score
            from hazardous_facility hf
            left join assign_inspector_hf aih on hf.id = aih.hf_id and aih.interval_id = :intervalId
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
            where hf.region_id = :regionId
            and aih.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegion(Pageable pageable, Integer regionId, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            hf.address,
            hf.legal_name as legalName,
            null as inspectorName,
            null as assignId,
            scores.total_score as score
            from hazardous_facility hf
            left join assign_inspector_hf aih on hf.id = aih.hf_id and aih.interval_id = :intervalId
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
            where hf.legal_tin = :legalTin
            and aih.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTin(Pageable pageable, Long legalTin, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            hf.address,
            hf.legal_name as legalName,
            null as inspectorName,
            null as assignId,
            scores.total_score as score
            from hazardous_facility hf
            left join assign_inspector_hf aih on hf.id = aih.hf_id and aih.interval_id = :intervalId
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
            where hf.registry_number = :registryNumber
            and aih.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumber(Pageable pageable, String registryNumber, Integer intervalId);

    @Query(value = """
            select hf.id as id,
            registry_number as registryNumber,
            hf.name as name,
            legal_tin as legalTin,
            hf.address,
            hf.legal_name as legalName,
            null as inspectorName,
            null as assignId,
            scores.total_score as score
            from hazardous_facility hf
            left join assign_inspector_hf aih on hf.id = aih.hf_id and aih.interval_id = :intervalId
            left join (
                select hazardous_facility_id, sum(score) as total_score
                from hf_risk_indicator
                where risk_analysis_interval_id = :intervalId
                group by hazardous_facility_id
            ) as scores on hf.id = scores.hazardous_facility_id
            where aih.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByInterval(Pageable pageable, Integer intervalId);

    @Query(value = """
            select distinct(region_id)
                from hazardous_facility
                where legal_tin = :tin
            """, nativeQuery = true)
    Set<Integer> getAllRegionIdByLegalTin(Long tin);

    Optional<HazardousFacility> findByIdAndProfileId(UUID id, UUID profileId);

    Optional<HazardousFacility> findByRegistryNumber(String hfRegistryNumber);

    List<HazardousFacility> findAllByLegalTin(Long tin);

    Optional<HazardousFacility> findByRegistryNumberAndLegalTinAndActive(String registryNumber, Long legalTin, Boolean active);

    @Query(nativeQuery = true, value = """
            select count(hf.id) filter ( where hf.deactivation_date is null or :date <= hf.deactivation_date )      as active,
                   count(hf.id) filter ( where hf.deactivation_date is not null and :date >= hf.deactivation_date ) as inactive
            from hazardous_facility hf
            where hf.region_id = :regionId
              and hf.registration_date <= :date
            """)
    HfCountByStatusView countStatusByDateAndRegionId(LocalDate date, Integer regionId);
}
