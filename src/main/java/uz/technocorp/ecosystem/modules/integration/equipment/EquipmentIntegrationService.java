package uz.technocorp.ecosystem.modules.integration.equipment;

import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.integration.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.integration.equipment.dto.InfoDto;

/**
 * @author Suxrob
 * @version 1.0
 * @created 21.07.2025
 * @since v1.0
 */
public interface EquipmentIntegrationService {

    InfoDto<EquipmentInfoDto> getEquipmentInfo(String tinOrPin, EquipmentType type);

}
