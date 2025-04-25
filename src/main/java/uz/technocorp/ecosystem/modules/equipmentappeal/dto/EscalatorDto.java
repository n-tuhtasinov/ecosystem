package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 25.04.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EscalatorDto extends EquipmentAppealDto implements AppealDto {

    @NotBlank(message = "O'tkazish qobilyati jo'natilmadi")
    private String passengersPerMinute;

    @NotBlank(message = "Uzunligi jo'natilmadi")
    private String length;

    @NotBlank(message = "Tezligi jo'natilmadi")
    private String speed;

    @NotBlank(message = "Ko'tarish balandligi jo'natilmadi")
    private String height;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_ESCALATOR;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
