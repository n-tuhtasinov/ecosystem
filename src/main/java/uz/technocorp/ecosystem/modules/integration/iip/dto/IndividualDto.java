package uz.technocorp.ecosystem.modules.integration.iip.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 14.05.2025
 * @since v1.0
 */
public record IndividualDto(

        @NotBlank(message = "JSHSHIR jo'natilmadi")
        @Pattern(regexp = "^\\d{14}$", message = "JSHSHIR 14ta raqamdan iborat bo'lishi kerak")
        String pin,

        @NotNull(message = "Tug'ilgan kun jo'natilmadi")
        LocalDate birthDate
) {
}
