package uz.technocorp.ecosystem.modules.region.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public record RegionDto(
        @NotBlank(message = "Viloyat nomi jo'natilmadi")
        String name,
        @NotNull(message = "Viloyat soatosi jo'natilmadi")
        Integer soato
) {
}
