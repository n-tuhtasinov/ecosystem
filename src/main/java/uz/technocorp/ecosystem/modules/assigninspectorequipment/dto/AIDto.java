package uz.technocorp.ecosystem.modules.assigninspectorequipment.dto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
public record AIDto(UUID inspectorId, UUID objectId, Integer intervalId) {
}
