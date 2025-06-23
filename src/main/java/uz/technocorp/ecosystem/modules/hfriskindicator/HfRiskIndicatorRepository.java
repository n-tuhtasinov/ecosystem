package uz.technocorp.ecosystem.modules.hfriskindicator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public interface HfRiskIndicatorRepository extends JpaRepository<HfRiskIndicator, UUID> {

    @Query("SELECT h FROM HfRiskIndicator h WHERE h.hazardousFacilityId = :hfId AND h.riskAnalysisInterval.id = :intervalId")
    List<HfRiskIndicator> findAllByQuarter(Integer intervalId, UUID hfId);


    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description,
            file_path as filePath,
            score_value as scoreValue,
            file_date as fileDate,
            cancelled_date as cancelledDate
            from hf_risk_indicator
            where (hazardous_facility_id = :id or hazardous_facility_id is null)
            and risk_analysis_interval_id = :intervalId
            and tin = :tin
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByHfIdAndTinAndDate(UUID id, Long tin, Integer intervalId);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description,
            file_path as filePath,
            score_value as scoreValue,
            file_date as fileDate,
            cancelled_date as cancelledDate
            from hf_risk_indicator
            where tin = :tin
            and hazardous_facility_id is null
            and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByTinAndDate(Long tin, Integer intervalId);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description,
            file_path as filePath,
            score_value as scoreValue,
            file_date as fileDate,
            cancelled_date as cancelledDate
            from hf_risk_indicator
            where ((tin = :tin and hazardous_facility_id is null) or hazardous_facility_id = :id)
            and risk_analysis_interval_id = :intervalId
            and file_path is not null
            and score != 0
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllFileContainsByTinAndDate(Long tin, UUID id, Integer intervalId);


    @Query(value = """
            select hazardous_facility_id as objectId,
            sum(score) as sumScore,
            tin
            from hf_risk_indicator
            where risk_analysis_interval_id = :intervalId
            group by hazardous_facility_id, tin
            """, nativeQuery = true)
    List<RiskAssessmentDto> findAllGroupByHfIdAndTin(Integer intervalId);


}
