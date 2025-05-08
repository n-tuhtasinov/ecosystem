package uz.technocorp.ecosystem.modules.hf.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 08.05.2025
 * @since v1.0
 */
public record HfPeriodicUpdateDto(

        @NotBlank(message = "Mavsumiy o'zgartirish uchun asos file path jo'natilmadi")
        String periodicUpdateFilePath,

        @NotBlank(message = "Mavsumiy o'zgartirish uchun sabab jo'natilmadi")
        String periodicUpdateReason

) {
}
