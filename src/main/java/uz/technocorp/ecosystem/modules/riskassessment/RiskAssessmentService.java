package uz.technocorp.ecosystem.modules.riskassessment;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.riskassessment.projection.RiskAssessmentView;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.04.2025
 * @since v1.0
 */
public interface RiskAssessmentService {

    Page<RiskAssessmentView> getAll(int page, int size);
}
