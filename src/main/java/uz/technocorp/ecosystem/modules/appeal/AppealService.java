package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealStatusDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;

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
    Page<AppealCustom> getAppealCustoms(Map<String, String> params);
    UUID create(AppealDto dto, UUID profileId, String number);

}