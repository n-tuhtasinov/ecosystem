package uz.technocorp.ecosystem.modules.report;

import uz.technocorp.ecosystem.modules.report.dto.request.AppealStatusFilterDto;
import uz.technocorp.ecosystem.modules.report.dto.request.AppealTypeFilterDto;
import uz.technocorp.ecosystem.modules.report.dto.response.ReportByRegistryDto;
import uz.technocorp.ecosystem.modules.report.view.ReportByAppealStatusView;
import uz.technocorp.ecosystem.modules.report.view.ReportByAppealTypeView;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.08.2025
 * @since v1.0
 */
public interface ReportService {
    List<ReportByAppealStatusView> getAppealStatus(AppealStatusFilterDto filterDto);

    List<ReportByAppealTypeView> getAppealType(AppealTypeFilterDto filterDto);

    String getLabelByAppealType(String appealType);

    List<ReportByRegistryDto> getRegistry(LocalDate date);
}
