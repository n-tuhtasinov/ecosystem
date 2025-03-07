package uz.technocorp.ecosystem.modules.appeal.dto;

import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.02.2025
 * @since v1.0
 */
public record AppealStatusDto(UUID appealId, AppealStatus status, String description) {
}
