package uz.technocorp.ecosystem.modules.district.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public record DistrictDto(
        @NotBlank(message = "Tuman nomi jo'natilmadi")
        String name,
        @NotNull(message = "Tuman soatosi jo'natilmadi")
        Integer soato,
        @NotNull(message = "Tuman raqami jo'natilmadi")
        Integer number,
        @NotNull(message = "Viloyat IDsi jo'natilmadi")
        Integer regionId
) {
}
