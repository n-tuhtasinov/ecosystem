package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.shared.SkipDb;

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
public class UnofficialEscalatorDto extends UnofficialEquipmentAppealDto {

    @SkipDb
    @NotBlank(message = "O'tkazish qobilyati jo'natilmadi")
    private String passengersPerMinute;

    @SkipDb
    @NotBlank(message = "Uzunligi jo'natilmadi")
    private String length;

    @SkipDb
    @NotBlank(message = "Tezligi jo'natilmadi")
    private String speed;

    @SkipDb
    @NotBlank(message = "Ko'tarish balandligi jo'natilmadi")
    private String height;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.ESCALATOR;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_ESCALATOR;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public AppealMode getAppealMode() {
        return AppealMode.UNOFFICIAL;
    }

    public void buildParameters() {
        super.getParameters().put("passengersPerMinute", passengersPerMinute);
        super.getParameters().put("length", length);
        super.getParameters().put("speed", speed);
        super.getParameters().put("height", height);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
