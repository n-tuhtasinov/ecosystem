package uz.technocorp.ecosystem.modules.hftype.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public record HazardousFacilityTypeDto(@NotBlank(message = "Nomi kiritilmagan!") String name,
                                       @NotBlank(message = "Izoh kiritilmagan!") String description) {
}
