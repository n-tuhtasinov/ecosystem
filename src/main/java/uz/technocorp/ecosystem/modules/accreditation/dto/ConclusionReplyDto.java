package uz.technocorp.ecosystem.modules.accreditation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.07.2025
 * @since v1.0
 */
public record ConclusionReplyDto(

        @NotNull(message = "Ariza IDsi jo'natilmadi")
        UUID appealId,

        @NotBlank(message = "Xulosa jo'natilmadi")
        String conclusion
) {
}
