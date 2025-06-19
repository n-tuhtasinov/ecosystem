package uz.technocorp.ecosystem.modules.appeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
public record UploadFileDto(

        @NotNull(message = "Ariza IDsi jo'natilmadi")
        UUID appealId,

        @NotBlank(message = "Yuklanayotgan file pathi jo'natilmadi")
        String filePath,

        @NotBlank(message = "Yuklanayotgan filening field nomi jo'natilmadi")
        String fieldName
) {
}
