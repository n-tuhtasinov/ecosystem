package uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment.projection.HFRAssessmentView;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.04.2025
 * @since v1.0
 */
public interface HazardousFacilityRiskAssessmentService {

    Page<HFRAssessmentView> getAll(int page, int size);
}
