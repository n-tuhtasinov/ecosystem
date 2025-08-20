package uz.technocorp.ecosystem.modules.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealStatusFilterDto;
import uz.technocorp.ecosystem.modules.statistics.view.AppealStatusCountView;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.08.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {


    private final AppealRepository appealRepository;

    @Override
    public List<AppealStatusCountView> getAppealStatus(AppealStatusFilterDto filterDto) {
        if (filterDto.getStartDate() == null) {
            filterDto.setStartDate(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        }
        return appealRepository.countByAppealStatus(filterDto.getOwnerType().name(), filterDto.getStartDate(), filterDto.getEndDate());
    }
}
