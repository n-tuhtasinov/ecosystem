package uz.technocorp.ecosystem.modules.equipmentappeal.register;

import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.AttractionDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.AttractionPassportDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.EquipmentAppealDto;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 14.06.2025
 * @since v1.0
 */
public interface EquipmentAppealService {

    void setHfNameAndChildEquipmentName(EquipmentAppealDto dto);

    void setChildEquipmentNameAndChildEquipmentSortName(AttractionPassportDto dto);

    void setRequiredFields(AttractionDto dto);
}
