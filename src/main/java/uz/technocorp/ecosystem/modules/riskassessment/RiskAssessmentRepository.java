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

    @Query(value = """
            select ra.sum_score as sumScore,
                   ra.tin as tin,
                   pr.legal_address as address,
                   pr.legal_name as legalName,
                   pr.region_name as regionName,
                   pr.district_name as districtName,
                   hf.name as hazardousFacilityName
            from risk_assessment ra
                     join profile pr on ra.tin = pr.tin
                     join hazardous_facility hf on ra.hazardous_facility_id = hf.id
            """, nativeQuery = true)
    Page<RiskAssessmentView> getAll(Pageable pageable);
}
