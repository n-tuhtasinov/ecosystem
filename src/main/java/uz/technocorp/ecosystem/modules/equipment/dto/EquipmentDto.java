package uz.technocorp.ecosystem.modules.equipment.dto;

import lombok.Builder;
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
        UUID hazardousFacilityId,
        Integer childEquipmentId,
        String childEquipmentName,
        String factoryNumber,
        String model,
        String factory,
        String location,
        LocalDate manufacturedAt,
        LocalDate partialCheckDate,
        LocalDate fullCheckDate,
        Sphere sphere,
        Map<String, String> parameters,
        String attractionName,
        LocalDate acceptedAt,
        Integer childEquipmentSortId,
        String childEquipmentSortName,
        String country,
        Integer servicePeriod,
        RiskLevel riskLevel,
        String parentOrganization,
        LocalDate nonDestructiveCheckDate,
        String description,
        UUID inspectorId,
        Map<String, String> files,
        UUID attractionPassportId   // for only attraction
) {
}
