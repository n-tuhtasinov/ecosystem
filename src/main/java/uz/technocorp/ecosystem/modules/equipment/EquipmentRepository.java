package uz.technocorp.ecosystem.modules.equipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentRiskView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public interface EquipmentRepository extends JpaRepository<Equipment, UUID>, EquipmentRepo {

    @Query("select e.orderNumber from Equipment e where e.type = :equipmentType order by e.orderNumber desc limit 1")
    Optional<Long> getMax(EquipmentType equipmentType);

    @Query(value = """
            select distinct(region_id)
                from equipment
                where legal_tin = :tin
            """, nativeQuery = true)
    Set<Integer> getAllRegionIdByLegalTin(Long tin);


    @Query("""
                        select e from Equipment e
                         left join fetch e.hazardousFacility
                         left join fetch e.childEquipment
                         left join fetch e.childEquipmentSort
                         left join fetch e.attractionPassport
                         where e.id = :equipmentId
            """)
    Optional<Equipment> getEquipmentById(UUID equipmentId);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   p.full_name    as inspectorName,
                   aie.id         as assignId,
                   case
                        when e.type = 'ELEVATOR' then elev_scores.total_score
                        when e.type = 'ATTRACTION' then attr_scores.total_score
                   end as score
            from equipment e
                     inner join assign_inspector_equipment aie on e.id = aie.equipment_id
                     join users u on u.id = aie.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where e.region_id = :regionId
              and aie.interval_id = :intervalId
              and e.type = :equipmentType
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByRegionAndInterval(Pageable pageable, Integer regionId, Integer intervalId, String equipmentType);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   p.full_name    as inspectorName,
                   aie.id         as assignId,
                   case
                        when e.type = 'ELEVATOR' then elev_scores.total_score
                        when e.type = 'ATTRACTION' then attr_scores.total_score
                   end as score
            from equipment e
                     inner join assign_inspector_equipment aie on e.id = aie.equipment_id
                     join users u on u.id = aie.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where aie.inspector_id = :inspectorId
              and aie.interval_id = :intervalId
              and e.type = :equipmentType
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByInspectorIdAndInterval(Pageable pageable, UUID inspectorId, Integer intervalId, String equipmentType);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   p.full_name    as inspectorName,
                   aie.id         as assignId,
                   case
                        when e.type = 'ELEVATOR' then elev_scores.total_score
                        when e.type = 'ATTRACTION' then attr_scores.total_score
                   end as score
            from equipment e
                     inner join assign_inspector_equipment aie on e.id = aie.equipment_id
                     join users u on u.id = aie.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where e.legal_tin = :legalTin
              and aie.interval_id = :intervalId
              and e.type = :equipmentType
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByLegalTinAndInterval(Pageable pageable, Long legalTin, Integer intervalId, String equipmentType);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   p.full_name    as inspectorName,
                   aie.id         as assignId,
                   case
                        when e.type = 'ELEVATOR' then elev_scores.total_score
                        when e.type = 'ATTRACTION' then attr_scores.total_score
                   end as score
            from equipment e
                     inner join assign_inspector_equipment aie on e.id = aie.equipment_id
                     join users u on u.id = aie.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where e.legal_tin = :legalTin
              and aie.interval_id = :intervalId
              and e.type = :equipmentType
              and aie.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByLegalTinAndIntervalAndInspectorId(Pageable pageable, Long legalTin, Integer intervalId, String equipmentType, UUID inspectorId);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   p.full_name    as inspectorName,
                   aie.id         as assignId,
                   case
                        when e.type = 'ELEVATOR' then elev_scores.total_score
                        when e.type = 'ATTRACTION' then attr_scores.total_score
                   end as score
            from equipment e
                     inner join assign_inspector_equipment aie on e.id = aie.equipment_id
                     join users u on u.id = aie.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where e.registry_number = :registryNumber
              and aie.interval_id = :intervalId
              and e.type = :equipmentType
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByRegistryNumberAndInterval(Pageable pageable, String registryNumber, Integer intervalId, String equipmentType);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   p.full_name    as inspectorName,
                   aie.id         as assignId,
                   case
                        when e.type = 'ELEVATOR' then elev_scores.total_score
                        when e.type = 'ATTRACTION' then attr_scores.total_score
                   end as score
            from equipment e
                     inner join assign_inspector_equipment aie on e.id = aie.equipment_id
                     join users u on u.id = aie.inspector_id
                     join profile p on p.id = u.profile_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where e.registry_number = :registryNumber
              and aie.interval_id = :intervalId
              and e.type = :equipmentType
              and aie.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByRegistryNumberAndIntervalAndInspectorId(Pageable pageable, String registryNumber, Integer intervalId, String equipmentType, UUID inspectorId);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   null           as inspectorName,
                   null           as assignId,
                   case
                        when e.type = 'ELEVATOR' then elev_scores.total_score
                        when e.type = 'ATTRACTION' then attr_scores.total_score
                   end as score
            from equipment e
                     left join assign_inspector_equipment aie on e.id = aie.equipment_id and aie.interval_id = :intervalId
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where e.region_id = :regionId
              and aie.id is null
              and e.type = :equipmentType
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByRegion(Pageable pageable, Integer regionId, String equipmentType, Integer intervalId);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   null           as inspectorName,
                   null           as assignId,
                   case
                       when e.type = 'ELEVATOR' then elev_scores.total_score
                       when e.type = 'ATTRACTION' then attr_scores.total_score
                       end as score
            from equipment e
                     left join assign_inspector_equipment aie on e.id = aie.equipment_id and aie.interval_id = :intervalId
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where e.legal_tin = :legalTin
              and aie.id is null
              and e.type = :equipmentType
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByLegalTin(Pageable pageable, Long legalTin, String equipmentType, Integer intervalId);

    @Query(value = """
            select e.id           as id,
                   e.registry_number as factoryNumber,
                   e.type         as name,
                   e.legal_tin    as legalTin,
                   address,
                   e.legal_name   as legalName,
                   null           as inspectorName,
                   null           as assignId,
                   case
                   when e.type = 'ELEVATOR' then elev_scores.total_score
                   when e.type = 'ATTRACTION' then attr_scores.total_score
                   end as score
            from equipment e
                     left join assign_inspector_equipment aie on e.id = aie.equipment_id and aie.interval_id = :intervalId
                     left join (
                        select equipment_id, sum(score) as total_score
                        from elevator_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as elev_scores on e.id = elev_scores.equipment_id
                     left join (
                        select equipment_id, sum(score) as total_score
                        from attraction_risk_indicator
                        where risk_analysis_interval_id = :intervalId
                        group by equipment_id
                     ) as attr_scores on e.id = attr_scores.equipment_id
            where e.registry_number = :registryNumber
              and aie.id is null
              and e.type = :equipmentType
            """, nativeQuery = true)
    Page<EquipmentRiskView> getAllByRegistryNumber(Pageable pageable, String registryNumber, String equipmentType, Integer intervalId);

    Optional<Equipment> findByRegistryNumber(String registryNumber);

    @Query("select e from Equipment e join fetch e.childEquipment join fetch e.childEquipmentSort where e.registryNumber = :registryNumber")
    Optional<Equipment> findFetchedEquipmentByRegistryNumber(String registryNumber);

    List<Equipment> findAllByLegalTinAndType(Long tin, EquipmentType type);
}
