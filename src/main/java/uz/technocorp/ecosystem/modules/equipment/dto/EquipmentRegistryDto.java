package uz.technocorp.ecosystem.modules.equipment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.enums.RiskLevel;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Suxrob
 * @version 1.0
 * @created 07.08.2025
 * @since v1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRegistryDto {

    private EquipmentType type;
    private String registryNumber;
    private LocalDate registrationDate;
    private LocalDate manufacturedAt;
    private String factory;
    private String factoryNumber;
    private String model;
    private Map<String, String> parameters;

    private String attractionName;
    private RiskLevel riskLevel;
}
