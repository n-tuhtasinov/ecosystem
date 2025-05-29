package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LpgPoweredDto extends EquipmentAppealDto {

    @NotBlank(message = "Hajm jo'natilmadi")
    private String capacity;

    @NotBlank(message = "Bosim jo'natilmadi")
    private String pressure;

    @NotBlank(message = "Yoqilg'i jo'natilmadi")
    private String fuel;

    @NotBlank(message = "Gaz ta'minoti loyihasi fayli uchun path jo'natilmadi")
    private String gasSupplyProjectPath;


    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_LPG_POWERED;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
