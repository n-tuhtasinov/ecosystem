package uz.technocorp.ecosystem.modules.dangerousObjectType.dto;

import jakarta.validation.constraints.NotBlank;

public record DangerousObjectTypeDto(@NotBlank(message = "Nomi kiritilmagan!") String name,
                                     @NotBlank(message = "Izoh kiritilmagan!") String description) {
}
