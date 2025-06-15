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

//    @Query(value = """
//            select ra.sum_score as sumScore,
//            ra.tin as tin,
//            pr.legal_address as address,
//            pr.legal_name as legalName,
//            pr.region_name as regionName,
//            pr.district_name as districtName,
//            hf.name as hazardousFacilityName
//            from hf_risk_assessment ra
//            join profile pr on ra.tin = pr.tin
//            join hazardous_facility hf on ra.hazardous_facility_id = hf.id
//            """, nativeQuery = true)
//    Page<RiskAssessmentView> getAll(Pageable pageable);

//    @Query(value = """
//            select ra.sum_score as sumScore,
//                   ra.tin as tin,
//                   pr.legal_address as address,
//                   pr.legal_name as legalName,
//                   pr.region_name as regionName,
//                   pr.district_name as districtName,
//                   hf.name as hazardousFacilityName
//            from risk_assessment ra
//                    join (
//                        select tin, MAX(sum_score) as max_score
//                        from risk_assessment
//                        group by tin
//                        ) as max_scores
//                        on ra.tin = max_scores.tin and ra.sum_score = max_scores.max_score
//                    join profile pr on ra.tin = pr.tin
//                    join hazardous_facility hf on ra.hazardous_facility_id = hf.id
//            where pr.region_id = :regionId
//            """, nativeQuery = true)
//    Page<RiskAssessmentView> getAllByMaxSumScore(Pageable pageable, Integer regionId);


    @Query(value = """
            select ra.id as id,
                hf.name as name,
                sum_score as score,
                hf.registry_number as registryNumber,
                hf.address as address
                from risk_assessment ra
                join hazardous_facility hf on ra.hazardous_facility_id = hf.id
                where ra.tin = :tin and hf.region_id = :regionId
                and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    Page<RiskAssessmentView> getAllHfByTinAndRegionId(Pageable pageable, Integer regionId, Integer intervalId, Long tin);

    @Query(value = """
            select ra.id as id,
                irs.registry_number as name,
                sum_score as score,
                irs.registry_number as registryNumber,
                irs.address as address
                from risk_assessment ra
                join ionizing_radiation_source irs on ra.ionizing_radiation_source_id = irs.id
                where ra.tin = :tin and irs.region_id = :regionId
                and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    Page<RiskAssessmentView> getAllIrsByTinAndRegionId(Pageable pageable, Integer regionId, Integer intervalId, Long tin);

    @Query(value = """
            select ra.id as id,

                sum_score as score,
                e.registry_number as registryNumber,
                e.address as address
                from risk_assessment ra
                join equipment e on ra.equipment_id = e.id
                where ra.tin = :tin and e.region_id = :regionId
                and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    Page<RiskAssessmentView> getAllEquipmentsByTinAndRegionId(Pageable pageable, Integer regionId, Integer intervalId, Long tin);
}
