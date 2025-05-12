package uz.technocorp.ecosystem.modules.hf.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 08.05.2025
 * @since v1.0
 */
public record HfDeregisterDto(

        @NotBlank(message = "Ro'yhatdan chiqarish uchun asos file path jo'natilmadi")
        String deregisterFilePath,

        @NotBlank(message = "Ro'yhatdan chiqarish uchun sabab jo'natilmadi")
        String deregisterReason

) {
}
