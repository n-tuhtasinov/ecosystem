package uz.technocorp.ecosystem.publics.dto;

import jakarta.validation.constraints.NotBlank;

public record SimpleDto(@NotBlank(message = "Qiymat bo'sh bo'lmasligi kerak!") String name) {
}
