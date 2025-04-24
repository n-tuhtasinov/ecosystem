package uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.HFRiskIndicatorView;

import java.time.LocalDateTime;
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

    @Query("SELECT h FROM HazardousFacilityRiskIndicator h WHERE h.hazardousFacilityId = :hfId AND h.createdAt BETWEEN :startDate AND :endDate")
    List<HazardousFacilityRiskIndicator> findAllByQuarter(
            LocalDateTime startDate,
            LocalDateTime endDate,
            UUID hfId
    );


    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description
            from hazardous_facility_risk_indicator
            where id = :id
            and created_at between :startDate and :endDate
            """, nativeQuery = true)
    List<HFRiskIndicatorView> findAllByHazardousFacilityIdAndDate(UUID id, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description
            from hazardous_facility_risk_indicator
            where tin = :tin
            and hazardous_facility_id is null
            and created_at between :startDate and :endDate
            """, nativeQuery = true)
    List<HFRiskIndicatorView> findAllByTinAndDate(Long tin, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query(value = """
            select cast(hazardous_facility_id as varchar) as objectId,
            sum(score),
            tin
            from hazardous_facility_risk_indicator
            where created_at between :startDate and :endDate
            group by hazardous_facility_id, tin
            """, nativeQuery = true)
    List<RiskAssessmentDto> findAllGroupByHazardousFacilityAndTin(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
