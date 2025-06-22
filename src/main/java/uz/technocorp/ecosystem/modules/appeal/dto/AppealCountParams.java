package uz.technocorp.ecosystem.modules.appeal.dto;

import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;

import java.util.UUID;

/**
* @author Nurmuhammad Tuhtasinov
* @version 1.0
* @since v1.0
* @created 22.06.2025
*/
public record AppealCountParams(
        AppealStatus status,
        Long legalTin,
        UUID executorId,
        Integer officeId
) {}
