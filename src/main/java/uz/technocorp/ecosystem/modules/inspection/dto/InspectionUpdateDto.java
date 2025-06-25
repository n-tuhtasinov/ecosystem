package uz.technocorp.ecosystem.modules.inspection.dto;

import java.time.LocalDate;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 23.05.2025
 * @since v1.0
 */
public record InspectionUpdateDto(String specialCode,
                                  LocalDate notificationLetterDate,
                                  String notificationLetterPath,
                                  String orderPath,
                                  String schedulePath,
                                  String programPath,
                                  String measuresPath,
                                  String resultPath) {
}
