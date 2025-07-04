package uz.technocorp.ecosystem.modules.appealexecutionprocess.dto;

import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public record AppealExecutionProcessDto(
        UUID appealId,
        AppealStatus appealStatus,
        String description) {
}
