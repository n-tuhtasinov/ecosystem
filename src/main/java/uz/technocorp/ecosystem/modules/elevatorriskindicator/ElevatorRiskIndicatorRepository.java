package uz.technocorp.ecosystem.modules.elevatorriskindicator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.irsriskindicator.IrsRiskIndicator;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public interface ElevatorRiskIndicatorRepository extends JpaRepository<ElevatorRiskIndicator, UUID> {

    @Query("SELECT h FROM ElevatorRiskIndicator h WHERE h.equipmentId = :equipmentId AND h.riskAnalysisInterval.id = :intervalId")
    List<ElevatorRiskIndicator> findAllByQuarter(
            Integer intervalId,
            UUID equipmentId
    );


    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description
            from elevator_risk_indicator
            where (equipment_id = :id or equipment_id is null)
            and risk_analysis_interval_id = :intervalId
            and tin = :tin
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByEquipmentIdAndTinAndDate(UUID id, Long tin, Integer intervalId);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description
            from elevator_risk_indicator
            where tin = :tin
            and equipment_id is null
            and risk_analysis_interval_id = :intervalId
            """, nativeQuery = true)
    List<RiskIndicatorView> findAllByTinAndDate(Long tin, Integer intervalId);

    @Query(value = """
            select cast(equipment_id as varchar) as objectId,
            sum(score),
            tin
            from elevator_risk_indicator
            where risk_analysis_interval_id = :intervalId
            group by equipment_id, tin
            """, nativeQuery = true)
    List<RiskAssessmentDto> findAllGroupByEquipmentAndTin(Integer intervalId);
}
