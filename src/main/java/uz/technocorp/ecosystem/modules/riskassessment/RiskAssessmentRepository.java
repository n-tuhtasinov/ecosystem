package uz.technocorp.ecosystem.modules.riskassessment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.riskassessment.projection.RiskAssessmentView;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.04.2025
 * @since v1.0
 */
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, UUID> {


    @Query(value = """
            select ra.id as id,
                hf.name as name,
                sum_score as score,
                hf.address as address,
                r.name as regionName,
                d.name as districtName
                from risk_assessment ra
                join hazardous_facility hf on ra.hazardous_facility_id = hf.id
                join region r on hf.region_id = r.id
                join district d on hf.district_id = d.id
                where ra.tin = :tin and hf.region_id = :regionId
                and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    Page<RiskAssessmentView> getAllHfByTinAndRegionId(Pageable pageable, Integer regionId, Integer intervalId, Long tin);

    @Query(value = """
            select ra.id as id,
                'INM' as name,
                sum_score as score,
                irs.address as address,
                r.name as regionName,
                d.name as districtName
                from risk_assessment ra
                join ionizing_radiation_source irs on ra.ionizing_radiation_source_id = irs.id
                join region r on irs.region_id = r.id
                join district d on irs.district_id = d.id
                where ra.tin = :tin and irs.region_id = :regionId
                and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    Page<RiskAssessmentView> getAllIrsByTinAndRegionId(Pageable pageable, Integer regionId, Integer intervalId, Long tin);

    @Query(value = """
            select ra.id as id,
                e.type as name,
                sum_score as score,
                e.address as address,
                e.region_name as regionName,
                e.district_name as districtName
                from risk_assessment ra
                join equipment e on ra.equipment_id = e.id
                where ra.tin = :tin and e.region_id = :regionId
                and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    Page<RiskAssessmentView> getAllEquipmentsByTinAndRegionId(Pageable pageable, Integer regionId, Integer intervalId, Long tin);
}
