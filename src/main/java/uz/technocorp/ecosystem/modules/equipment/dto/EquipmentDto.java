package uz.technocorp.ecosystem.modules.equipment.dto;

import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;
import uz.technocorp.ecosystem.modules.equipment.enums.Sphere;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public record EquipmentDto(
//        EquipmentType type,
//        UUID appealId,
//        String number,
//        Long orderNumber,
//        Long legalTin,
//        String legalName,
        UUID hazardousFacilityId,
        Integer childEquipmentId,
        String factoryNumber,
//        Integer regionId,
//        String regionName,
//        Integer districtId,
//        String address,
        String model,
        String factory,
        String location,
        LocalDate manufacturedAt,
        LocalDate partialCheckDate,
        LocalDate fullCheckDate,
        Sphere sphere,

//        String oldNumber,
        Map<String, String> parameters,
//        String boomLength,
//        String liftingCapacity,
//        String capacity,
//        String environment,
//        String pressure,
//        Sphere sphere,
//        String stopCount,
//        String length,
//        String speed,
//        String height,
//        String passengersPerMinute,
//        String passengerCount,
//        String diameter,
//        String thickness,
        String attractionName,
        LocalDate acceptedAt,
        Integer childEquipmentSortId,
        String country,
        Integer servicePeriod,
        RiskLevel riskLevel,
        String parentOrganization,
        LocalDate nonDestructiveCheckDate,
//        String temperature,
//        String density,
//        String fuel,
//        String labelPath,
        String description,
        UUID inspectorId,
        Map<String, String> files


//        String saleContractPath,
//        String equipmentCertPath,
//        String assignmentDecreePath,
//        String expertisePath,
//        String installationCertPath,
//        String additionalFilePath,
//        String passportPath,
//        String techReadinessActPath,
//        String seasonalReadinessActPath,
//        String safetyDecreePath,
//        String gasSupplyProjectPath,
//        String technicalManualPath,
//        String serviceManualPath,
//        String technicalJournalPath,
//        String acceptanceFilePath,
//        String routeInfoPath,
//        String conformityCertPath,
//        String safetyUsageReportPath,
//        String insurancePolicyPath,
//        String biomechanicalRiskFilePath,
//        String technicalStatusActPath,
//        String usageRightsPath
) {
}
