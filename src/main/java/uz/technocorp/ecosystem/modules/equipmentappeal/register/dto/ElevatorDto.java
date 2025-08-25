package uz.technocorp.ecosystem.modules.equipmentappeal.register.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealMode;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.enums.Sphere;
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
public class ElevatorDto extends EquipmentAppealDto {

    @NotNull(message = "Soha tanlanmadi")
    private Sphere sphere;

    @SkipDb
    @NotBlank(message = "Yuk ko'tara olish qiymati jo'natilmadi")
    private String liftingCapacity;

    @SkipDb
    @NotBlank(message = "To'xtashlar soni jo'natilmadi")
    private String stopCount;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.ELEVATOR;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_ELEVATOR;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public AppealMode getAppealMode() {
        return AppealMode.OFFICIAL;
    }

    public void buildParameters() {
        super.getParameters().put("liftingCapacity", liftingCapacity);
        super.getParameters().put("stopCount", stopCount);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
