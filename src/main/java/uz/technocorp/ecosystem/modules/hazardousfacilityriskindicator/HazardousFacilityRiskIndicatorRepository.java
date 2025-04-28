package uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.RiskIndicatorView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public interface HazardousFacilityRiskIndicatorRepository extends JpaRepository<HazardousFacilityRiskIndicator, UUID> {
//    List<HazardousFacilityRiskIndicator> findByHazardousFacilityIdAndDate(UUID hfId, LocalDate date);

    @Query("SELECT h FROM HazardousFacilityRiskIndicator h WHERE h.hazardousFacilityId = :hfId AND h.riskAnalysisInterval.id = :intervalId")
    List<HazardousFacilityRiskIndicator> findAllByQuarter(
            Integer intervalId,
            UUID hfId
    );


    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description
            from hazardous_facility_risk_indicator
            where (hazardous_facility_id = :id or hazardous_facility_id is null)
            and risk_analysis_interval_id = :intervalId
            and tin = :tin
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByHFIdAndTinAndDate(UUID id, Long tin, Integer intervalId);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description
            from hazardous_facility_risk_indicator
            where tin = :tin
            and hazardous_facility_id is null
            and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByTinAndDate(Long tin, Integer intervalId);

    @Query(value = """
            select cast(hazardous_facility_id as varchar) as objectId,
            sum(score),
            tin
            from hazardous_facility_risk_indicator
            where risk_analysis_interval_id = :intervalId
            group by hazardous_facility_id, tin
            """, nativeQuery = true)
    List<RiskAssessmentDto> findAllGroupByHazardousFacilityAndTin(Integer intervalId);

}
