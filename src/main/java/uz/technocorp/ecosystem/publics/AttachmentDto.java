package uz.technocorp.ecosystem.publics;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public record AttachmentDto(UUID objectId, String path, String attachmentName) {
}
