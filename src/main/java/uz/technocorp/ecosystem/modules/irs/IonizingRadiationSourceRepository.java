package uz.technocorp.ecosystem.modules.irs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.irs.view.IrsRiskView;

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
            select irs.id         as id,
                   factory_number as factoryNumber,
                   'INM'          as name,
                   irs.legal_tin  as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name    as inspectorName,
                   ai.id          as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs ai on irs.id = ai.irs_id
                     join users u on u.id = ai.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.region_id = :regionId and ai.interval_id = :intervalId
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByRegionAndInterval(Pageable pageable, Integer regionId, Integer intervalId);


    @Query(value = """
            select irs.id         as id,
                   factory_number as factoryNumber,
                   'INM'          as name,
                   irs.legal_tin  as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name    as inspectorName,
                   ai.id          as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs ai on irs.id = ai.irs_id
                     join users u on u.id = ai.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where ai.inspector_id = :inspectorId and ai.interval_id = :intervalId
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByInspectorIdAndInterval(Pageable pageable, UUID inspectorId, Integer intervalId);

    @Query(value = """
            select irs.id         as id,
                   factory_number as factoryNumber,
                   'INM'          as name,
                   irs.legal_tin  as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name    as inspectorName,
                   ai.id          as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs ai on irs.id = ai.irs_id
                     join users u on u.id = ai.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.legal_tin = :legalTin and ai.interval_id = :intervalId
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByLegalTinAndInterval(Pageable pageable, Long legalTin, Integer intervalId);

    @Query(value = """
            select irs.id as id,
                   factory_number as factoryNumber,
                   'INM' as name,
                   irs.legal_tin as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name as inspectorName,
                   aii.id as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs aii on irs.id = aii.irs_id
                     join users u on aii.inspector_id = u.id
                     join profile p on u.profile_id = p.id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.legal_tin = :legalTin
              and aii.interval_id = :intervalId
              and aii.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByLegalTinAndIntervalAndInspectorId(Pageable pageable, Long legalTin, Integer intervalId, UUID inspectorId);

    @Query(value = """
            select irs.id as id,
                   factory_number as factoryNumber,
                   'INM' as name,
                   irs.legal_tin as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name as inspectorName,
                   aii.id as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs aii on irs.id = aii.irs_id
                     join users u on aii.inspector_id = u.id
                     join profile p on u.profile_id = p.id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.factory_number = :factoryNumber
              and aii.interval_id = :intervalId
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByFactoryNumberAndInterval(Pageable pageable, String factoryNumber, Integer intervalId);

    @Query(value = """
            select irs.id as id,
                   factory_number as factoryNumber,
                   'INM' as name,
                   irs.legal_tin as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name as inspectorName,
                   aii.id as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs aii on irs.id = aii.irs_id
                     join users u on aii.inspector_id = u.id
                     join profile p on u.profile_id = p.id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.factory_number = :factoryNumber
              and aii.interval_id = :intervalId
              and aii.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByFactoryNumberAndIntervalAndInspectorId(Pageable pageable, String factoryNumber, Integer intervalId, UUID inspectorId);

    @Query(value = """
            select irs.id as id,
                   factory_number as factoryNumber,
                   'INM' as name,
                   irs.legal_tin as legalTin,
                   address,
                   irs.legal_name as legalName,
                   null as inspectorName,
                   null as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     left join assign_inspector_irs aii on irs.id = aii.irs_id and aii.interval_id = :intervalId
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.region_id = :regionId
              and aii.id is null
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByRegion(Pageable pageable, Integer regionId, Integer intervalId);

    @Query(value = """
            select irs.id as id,
                   factory_number as factoryNumber,
                   'INM' as name,
                   irs.legal_tin as legalTin,
                   address,
                   irs.legal_name as legalName,
                   null as inspectorName,
                   null as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     left join assign_inspector_irs aii on irs.id = aii.irs_id and aii.interval_id = :intervalId
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.legal_tin = :legalTin
              and aii.id is null
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByLegalTin(Pageable pageable, Long legalTin, Integer intervalId);

    @Query(value = """
            select irs.id as id,
                   factory_number as factoryNumber,
                   'INM' as name,
                   irs.legal_tin as legalTin,
                   address,
                   irs.legal_name as legalName,
                   null as inspectorName,
                   null as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     left join assign_inspector_irs aii on irs.id = aii.irs_id and aii.interval_id = :intervalId
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.factory_number = :factoryNumber
              and aii.id is null
            """, nativeQuery = true)
    Page<IrsRiskView> getAllByFactoryNumber(Pageable pageable, String factoryNumber, Integer intervalId);



    /*@Query(value = """
            select irs.id         as id,
                   factory_number as factoryNumber,
                   irs.name       as name,
                   irs.legal_tin  as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name    as inspectorName,
                   ai.id          as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs ai on irs.id = ai.irs_id
                     join users u on u.id = ai.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.region_id = :regionId and ai.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegionAndInterval(Pageable pageable, Integer regionId, Integer intervalId);


    @Query(value = """
            select irs.id         as id,
                   factory_number as factoryNumber,
                   irs.name       as name,
                   irs.legal_tin  as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name    as inspectorName,
                   ai.id          as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs ai on irs.id = ai.irs_id
                     join users u on u.id = ai.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where ai.inspector_id = :inspectorId and ai.interval_id = :intervalId
            """, nativeQuery = true)
    Page<HfPageView> getAllByInspectorIdAndInterval(Pageable pageable, UUID inspectorId, Integer intervalId);

    @Query(value = """
            select irs.id         as id,
                   factory_number as factoryNumber,
                   irs.name       as name,
                   irs.legal_tin  as legalTin,
                   address,
                   irs.legal_name as legalName,
                   p.full_name    as inspectorName,
                   ai.id          as assignId,
                   coalesce(scores.total_score, 0) as score
            from ionizing_radiation_source irs
                     inner join assign_inspector_irs ai on irs.id = ai.irs_id
                     join users u on u.id = ai.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select ionizing_radiation_source_id, sum(score) as total_score
                        from irs_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by ionizing_radiation_source_id
                     ) as scores on irs.id = scores.ionizing_radiation_source_id
            where irs.legal_tin = :legalTin and ai.interval_id = :intervalId
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
            left join assign_inspector_irs aii on irs.id = aii.irs_id and aii.interval_id = :intervalId
            where irs.region_id = :regionId
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
            left join assign_inspector_irs aii on irs.id = aii.irs_id and aii.interval_id = :intervalId
            where irs.legal_tin = :legalTin
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
            left join assign_inspector_irs aii on irs.id = aii.irs_id and aii.interval_id = :intervalId
            where irs.registry_number = :registryNumber
            and aii.id is null
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumber(Pageable pageable, String registryNumber, Integer intervalId);*/
}
