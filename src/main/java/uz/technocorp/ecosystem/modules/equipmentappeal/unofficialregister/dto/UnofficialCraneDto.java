package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
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
 * @created 23.04.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnofficialCraneDto extends UnofficialEquipmentAppealDto {

    @SkipDb
    private String boomLength;

    @SkipDb
    private String liftingCapacity;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.CRANE;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_CRANE;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

//    @Override
//    public AppealMode getAppealMode() {
//        return AppealMode.UNOFFICIAL;
//    }

    public void buildParameters() {
        super.getParameters().put("boomLength", boomLength);
        super.getParameters().put("liftingCapacity", liftingCapacity);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
