package uz.technocorp.ecosystem.modules.inspection.helper;

import uz.technocorp.ecosystem.modules.user.dto.InspectorDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 03.06.2025
 * @since v1.0
 */
public record InspectionFullDto(
        UUID id,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        String specialCode,
        String schedulePath,
        String notificationLetterPath,
        LocalDate notificationLetterDate,
        String orderPath,
        String programPath,
        String measuresPath,
        String resultPath,
        List<InspectorDto> inspectors
) {
}
