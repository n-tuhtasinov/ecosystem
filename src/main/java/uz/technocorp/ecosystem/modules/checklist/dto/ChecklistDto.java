package uz.technocorp.ecosystem.modules.checklist.dto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
public record ChecklistDto(
        Integer templateId,
        String path,
        Integer intervalId,
        UUID objectId
) {
}
