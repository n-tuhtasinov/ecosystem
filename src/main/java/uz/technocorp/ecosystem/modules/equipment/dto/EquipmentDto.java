package uz.technocorp.ecosystem.modules.equipment.dto;

import lombok.*;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;
import uz.technocorp.ecosystem.modules.equipment.enums.Sphere;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EquipmentDto {

    private EquipmentType type;
    private UUID appealId;
    private String number;
    private Long orderNumber;
    private Long legalTin;
    private String legalName;
    private UUID hazardousFacilityId;
    private Integer childEquipmentId;
    private String factoryNumber;
    private Integer regionId;
    private String regionName;
    private Integer districtId;
    private String address;
    private String model;
    private String factory;
    private String location;
    private LocalDate manufacturedAt;
    private LocalDate partialCheckDate;
    private LocalDate fullCheckDate;
    private String oldNumber;
    private String boomLength;
    private String liftingCapacity;
    private String capacity;
    private String environment;
    private String pressure;
    private Sphere sphere;
    private String stopCount;
    private String length;
    private String speed;
    private String height;
    private String passengersPerMinute;
    private String passengerCount;
    private String diameter;
    private String thickness;
    private String rideName;
    private LocalDate acceptedAt;
    private Integer childEquipmentSortId;
    private String country;
    private Integer servicePeriod;
    private RiskLevel riskLevel;
    private String parentOrganization;
    private LocalDate nonDestructiveCheckDate;
    private String temperature;
    private String density;
    private String fuel;
    private String labelPath;
    private String description;
    private UUID inspectorId;
    private String saleContractPath;
    private String equipmentCertPath;
    private String assignmentDecreePath;
    private String expertisePath;
    private String installationCertPath;
    private String additionalFilePath;
    private String passportPath;
    private String techReadinessActPath;
    private String seasonalReadinessActPath;
    private String safetyDecreePath;
    private String gasSupplyProjectPath;
    private String rideFile1Path;
    private String rideFile2Path;
    private String rideFile3Path;
    private String rideFile4Path;
    private String rideFile5Path;
    private String rideFile6Path;
    private String rideFile7Path;
    private String rideFile8Path;
    private String rideFile9Path;
    private String rideFile10Path;
    private String rideFile11Path;


}
