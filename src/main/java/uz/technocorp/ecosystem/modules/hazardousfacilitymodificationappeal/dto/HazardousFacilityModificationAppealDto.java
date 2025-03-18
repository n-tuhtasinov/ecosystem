package uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.03.2025
 * @since v1.0
 */
public record HazardousFacilityModificationAppealDto(
        @NotNull(message = "Hisobga olish raqami kiritilmadi!")
        UUID hazardousFacilityId,
        @NotBlank(message = "Telefon raqami kiritilmadi!")
        String phoneNumber,
        @NotBlank(message = "Pochta manzili kiritilmadi!")
        String email,
        @NotBlank(message = "Xichoga o'zgartirish kiritish yoki ro'yxatdan chiqarish sababi kiritilmadi!")
        String reason,
        @NotBlank(message = "Ariza bayoni kiritilmadi!")
        String statement,
        @NotBlank(message = "Dalolatnoma biriktirilmadi!")
        String actPath,
        @NotBlank(message = "Ariza fayli biriktirilmadi!")
        String appealPath,
        @NotBlank(message = "Ariza turi tanlanmadi!")
        String appealType
) {
}
