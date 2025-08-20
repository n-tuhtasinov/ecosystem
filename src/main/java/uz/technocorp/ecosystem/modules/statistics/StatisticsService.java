package uz.technocorp.ecosystem.modules.statistics;

import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealStatusFilterDto;
import uz.technocorp.ecosystem.modules.statistics.dto.request.AppealTypeFilterDto;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealStatusView;
import uz.technocorp.ecosystem.modules.statistics.view.StatByAppealTypeView;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.08.2025
 * @since v1.0
 */
public interface StatisticsService {
    List<StatByAppealStatusView> getAppealStatus(AppealStatusFilterDto filterDto);

    List<StatByAppealTypeView> getAppealType(AppealTypeFilterDto filterDto);

    String getLabelByAppealType(String appealType);
}
