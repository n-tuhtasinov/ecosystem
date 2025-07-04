package uz.technocorp.ecosystem.modules.equipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;

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
                         left join fetch e.oldEquipment
                         left join fetch e.childEquipmentSort
                         left join fetch e.attractionPassport
                         where e.id = :equipmentId
            """)
    Optional<Equipment> getEquipmentById(UUID equipmentId);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName,
            p.full_name as inspectorName,
            aia.id as assignId
            from equipment e
            inner join assign_inspector_equipment aia on e.id = aia.equipment_id
            join users u on aia.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where e.region_id = :regionId
            and aia.interval_id = :intervalId
            and e.type = :equipmentType
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegionAndInterval(Pageable pageable, Integer regionId, Integer intervalId, String equipmentType);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName,
            p.full_name as inspectorName,
            aia.id as assignId
            from equipment e
            inner join assign_inspector_equipment aia on e.id = aia.equipment_id
            join users u on aia.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where aia.inspector_id = :inspectorId
            and aia.interval_id = :intervalId
            and e.type = :equipmentType
            """, nativeQuery = true)
    Page<HfPageView> getAllByInspectorIdAndInterval(Pageable pageable, UUID inspectorId, Integer intervalId, String equipmentType);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName,
            p.full_name as inspectorName,
            aia.id as assignId
            from equipment e
            inner join assign_inspector_equipment aia on e.id = aia.equipment_id
            join users u on aia.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where e.legal_tin = :legalTin
            and aia.interval_id = :intervalId
            and e.type = :equipmentType
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTinAndInterval(Pageable pageable, Long legalTin, Integer intervalId, String equipmentType);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName,
            p.full_name as inspectorName,
            aia.id as assignId
            from equipment e
            inner join assign_inspector_equipment aia on e.id = aia.equipment_id
            join users u on aia.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where e.legal_tin = :legalTin
            and aia.interval_id = :intervalId
            and e.type = :equipmentType
            and aia.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTinAndIntervalAndInspectorId(Pageable pageable, Long legalTin, Integer intervalId, String equipmentType, UUID inspectorId);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName,
            p.full_name as inspectorName,
            aia.id as assignId
            from equipment e
            inner join assign_inspector_equipment aia on e.id = aia.equipment_id
            join users u on aia.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where e.registry_number = :registryNumber
            and aia.interval_id = :intervalId
            and e.type = :equipmentType
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumberAndInterval(Pageable pageable, String registryNumber, Integer intervalId, String equipmentType);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName,
            p.full_name as inspectorName,
            aia.id as assignId
            from equipment e
            inner join assign_inspector_equipment aia on e.id = aia.equipment_id
            join users u on aia.inspector_id = u.id
            join profile p on u.profile_id = p.id
            where e.registry_number = :registryNumber
            and aia.interval_id = :intervalId
            and e.type = :equipmentType
            and aia.inspector_id = :inspectorId
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumberAndIntervalAndInspectorId(Pageable pageable, String registryNumber, Integer intervalId, String equipmentType, UUID inspectorId);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName
            from equipment e
            left join assign_inspector_equipment aia on e.id = aia.equipment_id and aia.interval_id = :intervalId
            where e.region_id = :regionId
            and aia.id is null
            and e.type = :equipmentType
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegion(Pageable pageable, Integer regionId, String equipmentType, Integer intervalId);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName
            from equipment e
            left join assign_inspector_equipment aia on e.id = aia.equipment_id and aia.interval_id = :intervalId
            where e.legal_tin = :legalTin
            and aia.id is null
            and e.type = :equipmentType
            """, nativeQuery = true)
    Page<HfPageView> getAllByLegalTin(Pageable pageable, Long legalTin, String equipmentType, Integer intervalId);

    @Query(value = """
            select e.id as id,
            registry_number as registryNumber,
            e.type as name,
            legal_tin as legalTin,
            address,
            e.legal_name as legalName
            from equipment e
            left join assign_inspector_equipment aia on e.id = aia.equipment_id and aia.interval_id = :intervalId
            where e.registry_number = :registryNumber
            and aia.id is null
            and e.type = :equipmentType
            """, nativeQuery = true)
    Page<HfPageView> getAllByRegistryNumber(Pageable pageable, String registryNumber, String equipmentType, Integer intervalId);

    Optional<Equipment> findByRegistryNumber(String registryNumber);

    @Query("select e from Equipment e join fetch e.childEquipment join fetch e.childEquipmentSort where e.registryNumber = :registryNumber")
    Optional<Equipment> findFetchedEquipmentByRegistryNumber(String registryNumber);
}
