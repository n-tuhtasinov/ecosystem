package uz.technocorp.ecosystem.modules.equipment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentParams{
    private EquipmentType type;
    private Integer page;
    private Integer size;
    private String search;
    private Integer regionId;
    private Integer districtId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}
