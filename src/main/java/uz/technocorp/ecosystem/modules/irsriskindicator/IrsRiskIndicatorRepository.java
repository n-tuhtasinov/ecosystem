package uz.technocorp.ecosystem.modules.irsriskindicator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.HazardousFacilityRiskIndicator;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.HFRiskIndicatorView;
import uz.technocorp.ecosystem.modules.irsriskindicator.view.IrsRiskIndicatorView;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
public interface IrsRiskIndicatorRepository extends JpaRepository<HazardousFacilityRiskIndicator, UUID> {
//    List<HazardousFacilityRiskIndicator> findByHazardousFacilityIdAndDate(UUID hfId, LocalDate date);

    @Query("SELECT h FROM IrsRiskIndicator h WHERE h.ionizingRadiationSourceId = :irsId AND h.createdAt BETWEEN :startDate AND :endDate")
    List<IrsRiskIndicator> findAllByQuarter(
            LocalDateTime startDate,
            LocalDateTime endDate,
            UUID irsId
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
    List<IrsRiskIndicatorView> findAllByHazardousFacilityIdAndDate(UUID id, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = """
            select cast(id as varchar) as id,
            indicator_type as indicatorType,
            score,
            description
            from irs_risk_indicator
            where tin = :tin
            and ionizing_radiation_source_id is null
            and created_at between :startDate and :endDate
            """, nativeQuery = true)
    List<IrsRiskIndicatorView> findAllByTinAndDate(Long tin, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query(value = """
            select cast(ionizing_radiation_source_id as varchar) as objectId,
            sum(score),
            tin
            from irs_risk_indicator
            where created_at between :startDate and :endDate
            group by ionizing_radiation_source_id, tin
            """, nativeQuery = true)
    List<RiskAssessmentDto> findAllGroupByHazardousFacilityAndTin(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
