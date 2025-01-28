package uz.technocorp.ecosystem.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotBlank(message = "Username jo'natilmadi")
        @Size(min = 5, message = "username 5 ta belgidan kam bo'lishi mumkin emas")
        String username,

        @NotBlank(message = "password jo'natilmadi")
        @Size(min = 8, message = "Parol 8 belgidan kam bo'lishi mumkin emas")
        String password
) {
}
