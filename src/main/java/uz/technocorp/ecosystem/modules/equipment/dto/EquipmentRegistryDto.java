package uz.technocorp.ecosystem.modules.equipment.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public record EquipmentRegistryDto(

        @NotNull(message = "Ariza IDsi jo'natilmadi")
        UUID appealId

) {
}
