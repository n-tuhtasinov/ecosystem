package uz.technocorp.ecosystem.modules.appeal.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
public record SetInspectorDto(

        @NotNull(message = "Ariza IDsi jo'natilmadi")
        UUID appealId,

        @NotNull(message = "Inspektor userIDsi jo'natilmadi")
        UUID inspectorId,

        @NotNull(message = "Ijro muddati belgilanmadi")
        LocalDate deadline,

        String resolution
) {}
