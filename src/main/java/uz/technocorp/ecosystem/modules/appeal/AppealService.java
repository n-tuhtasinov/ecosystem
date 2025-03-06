package uz.technocorp.ecosystem.modules.appeal;

import uz.technocorp.ecosystem.modules.appeal.dto.AppealStatusDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealService {

    void setInspector(SetInspectorDto dto);
    void changeAppealStatus(AppealStatusDto dto);
}