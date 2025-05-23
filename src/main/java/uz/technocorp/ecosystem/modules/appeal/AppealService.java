package uz.technocorp.ecosystem.modules.appeal;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.dto.*;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealService {

    void setInspector(SetInspectorDto dto);

    void changeAppealStatus(AppealStatusDto dto);

    Page<AppealCustom> getAppealCustoms(User user, Map<String, String> params);

    UUID create(AppealDto dto, User user);

    void update(UUID id, AppealDto dto);

    String preparePdfWithParam(HfAppealDto dto, User user);

    String prepareReplyPdfWithParam(User user, ReplyDto replyDto);

    void saveAndSign(User user, SignedAppealDto signedDto, HttpServletRequest request);

    void saveReplyAndSign(User user, SignedReplyDto replyDto, HttpServletRequest request);

    List<AppealViewByPeriod> getAllByPeriodAndInspector(User inspector, LocalDate startDate, LocalDate endDate);

    AppealViewById getById(UUID appealId);
}