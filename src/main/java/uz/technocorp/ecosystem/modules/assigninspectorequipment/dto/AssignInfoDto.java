package uz.technocorp.ecosystem.modules.assigninspectorequipment.dto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 11.06.2025
 * @since v1.0
 */
public record AssignInfoDto(UUID id, Integer intervalId,
                            String inspectorName, String date) {
}
