package uz.technocorp.ecosystem.modules.appeal.dto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
public record SetInspectorDto(UUID appealId, UUID inspectorId, String deadline) {
}
