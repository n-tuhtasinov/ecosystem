package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
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
public class UnofficialElevatorDto extends UnofficialEquipmentAppealDto {

    @NotNull(message = "Soha tanlanmadi")
    private Sphere sphere;

    @SkipDb
    private String liftingCapacity;

    @SkipDb
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
