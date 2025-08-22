package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.shared.SkipDb;

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
public class UnofficialHoistDto extends UnofficialEquipmentAppealDto {

    @SkipDb
    @NotBlank(message = "Ko'tarish balandligi jo'natilmadi")
    private String height;

    @SkipDb
    @NotBlank(message = "Yuk ko'tarish quvvati jo'natilmadi")
    private String liftingCapacity;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.HOIST;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_HOIST;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    public void buildParameters() {
        super.getParameters().put("height", height);
        super.getParameters().put("liftingCapacity", liftingCapacity);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
