package uz.technocorp.ecosystem.modules.appeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.dto.*;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.EquipmentAppealDto;
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

    void saveAndSign(User user, SignedAppealDto<? extends AppealDto> dto, HttpServletRequest request);

    void saveReplyAndSign(User user, SignedReplyDto replyDto, HttpServletRequest request);

    UUID create(AppealDto dto, User user); //TODO Barcha ariza yaratiladigan controllerlar saveAndSign ga o'tkazilgandan keyin Service dan o'chirib, ServiceImplda private method qilib qo'yish kerak

    void update(UUID id, AppealDto dto);

    void setInspector(SetInspectorDto dto);

    Page<AppealCustom> getAppealCustoms(User user, Map<String, String> params);

    List<AppealViewByPeriod> getAllByPeriodAndInspector(User inspector, LocalDate startDate, LocalDate endDate);

    AppealViewById getById(UUID appealId);

    String preparePdfWithParam(AppealDto dto, User user);

    String prepareReplyPdfWithParam(User user, ReplyDto replyDto);

    void reject(User user, RejectDto dto);

    void confirm(User user, ConfirmationDto dto);

    void setHfNameAndChildEquipmentName(EquipmentAppealDto dto);

    void setHfTypeName(HfAppealDto appealDto);
}