package uz.technocorp.ecosystem.modules.attachment.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 21.04.2025
 * @since v1.0
 */
public record AttachmentDto(@NotBlank String content) {
}
