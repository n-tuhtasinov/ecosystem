package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister;

import uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto.UnofficialAttractionDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto.UnofficialEquipmentAppealDto;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 25.08.2025
 * @since v1.0
 */
public interface UnofficialEquipmentAppealService {

    void setHfNameAndChildEquipmentName(UnofficialEquipmentAppealDto appealDto);

    void setRequiredFields(UnofficialAttractionDto dto);

}
