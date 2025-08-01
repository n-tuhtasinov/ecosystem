package uz.technocorp.ecosystem.modules.equipment.view;

import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;
import uz.technocorp.ecosystem.modules.equipment.enums.Sphere;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.06.2025
 * @since v1.0
 */
public record EquipmentViewById(
        LocalDate registrationDate,
        EquipmentType type,
        UUID appealId,
        String registryNumber,
        Long ownerIdentity,

        UUID hfId,
        String hfName,

        Integer childEquipmentId,
        String childEquipmentName,

        String factoryNumber,
        String address,
        String model,
        String factory,
        String location,
        LocalDate manufacturedAt,
        String oldRegistryNumber,

        Map<String, String> parameters,
        Sphere sphere,
        String attractionName,
        LocalDate acceptedAt,

        Integer childEquipmentSortId,
        String childEquipmentSortName,

        String country,
        Integer servicePeriod,
        RiskLevel riskLevel,
        String parentOrganization,
        LocalDate nonDestructiveCheckDate,
        UUID attractionPassportId,
        String attractionPassportRegistryNumber,
        String description,
        String inspectorName,
        Boolean isActive,

        Map<String, String> files,
        String registryFilePath

) {}
