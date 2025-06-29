package uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
public record RejectPassportDto(

        @NotNull(message = "Ariza IDsi jo'natilmadi")
        UUID appealId,

        @NotBlank(message = "Rad etish sababi jo'natilmadi")
        String conclusion
) {
}
