package uz.technocorp.ecosystem.modules.integration.iip.dto;

import jakarta.validation.constraints.*;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 14.05.2025
 * @since v1.0
 */
public record LegalDto(
        @NotBlank(message = "Tashkilot STIR jo'natilmadi")
        @Pattern(regexp = "^\\d{9}$", message = "STIR 9ta raqamdan iborat bo'lishi kerak")
        String tin
) {
}
