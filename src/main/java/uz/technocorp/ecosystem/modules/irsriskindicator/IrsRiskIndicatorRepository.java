package uz.technocorp.ecosystem.modules.irsriskindicator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public interface IrsRiskIndicatorRepository extends JpaRepository<IrsRiskIndicator, UUID> {

    @Query("SELECT h FROM IrsRiskIndicator h WHERE h.ionizingRadiationSourceId = :irsId AND h.riskAnalysisInterval.id = :intervalId")
    List<IrsRiskIndicator> findAllByQuarter(
            Integer intervalId,
            UUID irsId
    );


    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description,
            file_path as filePath,
            score_value as scoreValue,
            file_date as fileDate,
            cancelled_date as cancelledDate
            from irs_risk_indicator
            where risk_analysis_interval_id = :intervalId
            and tin = :tin
            and (ionizing_radiation_source_id = :id or ionizing_radiation_source_id is null)
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByIrsIdAndTinAndDate(UUID id, Long tin, Integer intervalId);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description,
            file_path as filePath,
            score_value as scoreValue,
            file_date as fileDate,
            cancelled_date as cancelledDate
            from irs_risk_indicator
            where tin = :tin
            and ionizing_radiation_source_id is null
            and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByTinAndDate(Long tin, Integer intervalId);

    @Query(value = """
            select cast(ionizing_radiation_source_id as varchar) as objectId,
            sum(score),
            tin
            from irs_risk_indicator
            where risk_analysis_interval_id = :intervalId
            group by ionizing_radiation_source_id, tin
            """, nativeQuery = true)
    List<RiskAssessmentDto> findAllGroupByIrsAndTin(Integer intervalId);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description,
            file_path as filePath,
            score_value as scoreValue,
            file_date as fileDate,
            cancelled_date as cancelledDate
            from irs_risk_indicator
            where tin = :tin
            and risk_analysis_interval_id = :intervalId
            and file_path is not null
            and score != 0
            """, nativeQuery = true)
    Page<RiskIndicatorView> findAllFileContainsByTinAndDate(Long tin, Integer intervalId, Pageable pageable);

}
