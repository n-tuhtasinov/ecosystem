package uz.technocorp.ecosystem.modules.childequipmentsort.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public record ChildEquipmentSortDto(
        @NotBlank(message = "Qurilma uchun ichki tip nomi jo'natilmadi")
        String name,

        @NotNull(message = "Qurilmaning ichki turi IDsi jo'natilmadi")
        Integer childEquipmentId
) {}
