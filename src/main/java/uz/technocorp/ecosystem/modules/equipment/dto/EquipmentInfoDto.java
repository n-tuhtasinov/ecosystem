package uz.technocorp.ecosystem.modules.equipment.dto;

import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 07.05.2025
 * @since v1.0
 */
public record EquipmentInfoDto(
        EquipmentType equipmentType,
        String registryNumber,
        Long orderNumber
) {
}
