package uz.technocorp.ecosystem.modules.report.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 21.08.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportByRegistryDto {
    private String officeName;
    private Integer activeHf = 0;
    private Integer inactiveHf = 0;
    private Integer activeEquipment = 0;
    private Integer inactiveEquipment = 0;
    private Integer expiredEquipment = 0;
    private Integer activeIrs = 0;
    private Integer inactiveIrs = 0;
}
