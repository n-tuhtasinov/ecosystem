package uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment.projection.HFRAssessmentView;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.04.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class HazardousFacilityRiskAssessmentServiceImpl implements HazardousFacilityRiskAssessmentService {
    private final HazardousFacilityRiskAssessmentRepository repository;
    @Override
    public Page<HFRAssessmentView> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "sum_score");
        return repository.getAll(pageable);
    }
}
