package uz.technocorp.ecosystem.modules.riskassessment;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.riskassessment.projection.RiskAssessmentView;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.04.2025
 * @since v1.0
 */
public interface RiskAssessmentService {

    Page<RiskAssessmentView> getAllHf(User user, Long tin, Integer regionId, Integer intervalId, int page, int size);
    Page<RiskAssessmentView> getAllIrs(User user, Long tin, Integer regionId, Integer intervalId, int page, int size);
    Page<RiskAssessmentView> getAllEquipments(User user, Long tin, Integer regionId, Integer intervalId, int page, int size);
}
