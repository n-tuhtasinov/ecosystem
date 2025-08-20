package uz.technocorp.ecosystem.modules.equipment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 20.08.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRiskParamsDto {

    private EquipmentType equipmentType;
    private User user;
    private Integer page;
    private Integer size;
    private Long legalTin;
    private String registryNumber;
    private Boolean isAssigned;
    private Integer intervalId;
}
