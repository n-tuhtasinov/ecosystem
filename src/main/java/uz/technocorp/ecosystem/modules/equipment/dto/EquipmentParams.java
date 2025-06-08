package uz.technocorp.ecosystem.modules.equipment.dto;

import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
public record EquipmentParams(
        EquipmentType type,
        Integer page,
        Integer size,
        Long legalTin,
        String registryNumber,
        Integer regionId,
        Integer districtId,
        LocalDate startDate,
        LocalDate endDate
) {
}
