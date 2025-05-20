package uz.technocorp.ecosystem.modules.office.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
public record OfficeDto(
        @NotBlank(message = "Hududiy bo'lim nomi kiritilmadi")
        String name,
        @NotNull(message = "Hududiy bo'lim qaysi viloyat uchunligi tanlanmadi")
        Integer regionId
) {
}
