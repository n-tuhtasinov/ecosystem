package uz.technocorp.ecosystem.modules.appeal.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.05.2025
 * @since v1.0
 */
public record ConfirmationDto(

        @NotNull(message = "Ariza IDsi jo'natilmadi")
        UUID appealId,

        @NotNull(message = "Document IDsi jo'natilmadi")
        UUID documentId
) {
}
