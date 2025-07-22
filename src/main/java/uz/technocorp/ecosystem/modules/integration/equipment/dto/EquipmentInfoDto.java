package uz.technocorp.ecosystem.modules.integration.equipment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Suxrob
 * @version 1.0
 * @created 22.07.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentInfoDto {

    private String type;
    private String childEquipmentName;
    private String registryNumber;
    private String factory;
    private String factoryNumber;
    private String regionName;
    private String districtName;
    private String address;
    private String model;
    private String location;
    private LocalDate manufacturedAt;
    private LocalDate partialCheckDate;
    private LocalDate fullCheckDate;
    private LocalDate registrationDate;
    private String registryFilePath;
    private Boolean isActive;
    private Map<String, String> parameters;
}
