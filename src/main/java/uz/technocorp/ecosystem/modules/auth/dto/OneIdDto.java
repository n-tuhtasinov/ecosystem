package uz.technocorp.ecosystem.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public record OneIdDto(
        @NotBlank(message = "OneId tomonidan olingan kod jo'natilmadi")
        String code
) {}
