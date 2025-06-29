package uz.technocorp.ecosystem.modules.inspection.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectorShortInfo;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionFullDto {

    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String specialCode;
    private String decreePath;
    private String decreeNumber;
    private LocalDate decreeDate;
    private String schedulePath;
    private String notificationLetterPath;
    private LocalDate notificationLetterDate;
    private String orderPath;
    private String programPath;
    private String measuresPath;
    private String resultPath;
    private List<InspectorShortInfo> inspectors;
}
