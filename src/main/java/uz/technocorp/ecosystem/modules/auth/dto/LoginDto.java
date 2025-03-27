package uz.technocorp.ecosystem.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public record LoginDto(
        @NotBlank(message = "Login jo'natilmadi")
        @Size(min = 5, message = "Login 5 ta belgidan kam bo'lishi mumkin emas")
        String username,

        @NotBlank(message = "Parol jo'natilmadi")
        @Size(min = 8, message = "Parol 8 ta belgidan kam bo'lishi mumkin emas")
        String password
) {
}
