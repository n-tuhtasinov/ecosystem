package uz.technocorp.ecosystem.modules.riskanalysisinterval;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;

import java.util.List;
import java.util.Optional;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.04.2025
 * @since v1.0
 */
public interface RiskAnalysisIntervalRepository extends JpaRepository<RiskAnalysisInterval, Integer> {

    List<RiskAnalysisInterval> findAllByYear(Integer year);

    Optional<RiskAnalysisInterval> findByStatus(RiskAnalysisIntervalStatus riskAnalysisIntervalStatus);
}
