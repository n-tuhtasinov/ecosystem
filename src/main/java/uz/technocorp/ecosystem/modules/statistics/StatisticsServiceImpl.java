package uz.technocorp.ecosystem.modules.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealStatusFilterDto;
import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealTypeFilterDto;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealStatusView;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealTypeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
    public List<StatByAppealStatusView> getAppealStatus(AppealStatusFilterDto filterDto) {
        if (filterDto.getStartDate() == null) {
            filterDto.setStartDate(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        }
        return appealRepository.countByAppealStatus(filterDto.getOwnerType().name(), filterDto.getStartDate(), filterDto.getEndDate());
    }

    @Override
    public List<StatByAppealTypeView> getAppealType(AppealTypeFilterDto dto) {
        Stream<AppealType> appealTypeStream = dto.getAppealTypes().isEmpty() ? Arrays.stream(AppealType.values()) : dto.getAppealTypes().stream();
        String[] appealTypes = appealTypeStream.map(Enum::name).toArray(String[]::new);

        if (dto.getStartDate() == null) {
            dto.setStartDate(LocalDate.of(LocalDate.now().getYear(), 1, 1));
        }

        return appealRepository.countByAppealType(dto.getOwnerType().name(), dto.getStartDate(), dto.getEndDate(), appealTypes);
    }

    @Override
    public String getLabelByAppealType(String appealType) {
        return AppealType.valueOf(appealType).label;
    }
}
