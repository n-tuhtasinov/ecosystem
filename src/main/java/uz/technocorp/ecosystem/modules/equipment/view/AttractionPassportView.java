package uz.technocorp.ecosystem.modules.equipment.view;

import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 16.06.2025
 * @since v1.0
 */
public record AttractionPassportView(
        UUID attractionPassportId,
        String attractionName,
        String childEquipment,
        String childEquipmentSort,
        LocalDate manufacturedAt,
        LocalDate acceptedAt,
        String factoryNumber,
        String country,
        RiskLevel riskLevel
) {}
