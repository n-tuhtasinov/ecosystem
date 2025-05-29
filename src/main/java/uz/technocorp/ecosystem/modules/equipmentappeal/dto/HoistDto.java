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
public class HoistDto extends EquipmentAppealDto {

    @NotBlank(message = "Ko'tarish balandligi jo'natilmadi")
    private String height;

    @NotBlank(message = "Yuk ko'tarish quvvati jo'natilmadi")
    private String liftingCapacity;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_HOIST;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
