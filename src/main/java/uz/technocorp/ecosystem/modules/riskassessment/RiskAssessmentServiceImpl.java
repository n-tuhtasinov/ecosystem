package uz.technocorp.ecosystem.modules.riskassessment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.riskassessment.projection.RiskAssessmentView;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.04.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class RiskAssessmentServiceImpl implements RiskAssessmentService {
    private final RiskAssessmentRepository repository;
    @Override
    public Page<RiskAssessmentView> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "sum_score");
        return repository.getAll(pageable);
    }
}
