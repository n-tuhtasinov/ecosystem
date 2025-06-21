package uz.technocorp.ecosystem.modules.inspection.helper;

import lombok.*;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.05.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionCustom {

    private UUID id;
    private Long tin;
    private String regionName;
    private String districtName;
    private String legalName;
    private String legalAddress;
}
