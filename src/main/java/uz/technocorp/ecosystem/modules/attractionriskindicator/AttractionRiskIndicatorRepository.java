package uz.technocorp.ecosystem.modules.attractionriskindicator;

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
 * @created 28.04.2025
 * @since v1.0
 */
public interface AttractionRiskIndicatorRepository extends JpaRepository<AttractionRiskIndicator, UUID> {

    @Query("SELECT h FROM AttractionRiskIndicator h WHERE h.equipmentId = :equipmentId AND h.riskAnalysisInterval.id = :intervalId")
    List<AttractionRiskIndicator> findAllByQuarter(
            Integer intervalId,
            UUID equipmentId
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
            from attraction_risk_indicator
            where (equipment_id = :id or equipment_id is null)
            and risk_analysis_interval_id = :intervalId
            and tin = :tin
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByEquipmentIdAndTinAndDate(UUID id, Long tin, Integer intervalId);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description,
            file_path as filePath,
            score_value as scoreValue,
            file_date as fileDate,
            cancelled_date as cancelledDate
            from attraction_risk_indicator
            where tin = :tin
            and equipment_id is null
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
            from attraction_risk_indicator
            where ((tin = :tin and equipment_id is null) or equipment_id = :id)
            and risk_analysis_interval_id = :intervalId
            and file_path is not null
            and score != 0
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllFileContainsByTinAndDate(Long tin, Integer intervalId, UUID id);

    @Query(value = """
            select equipment_id as objectId,
            sum(score) as sumScore,
            tin
            from attraction_risk_indicator
            where risk_analysis_interval_id = :intervalId
            group by equipment_id, tin
            """, nativeQuery = true)
    List<RiskAssessmentDto> findAllGroupByEquipmentAndTin(Integer intervalId);
}
