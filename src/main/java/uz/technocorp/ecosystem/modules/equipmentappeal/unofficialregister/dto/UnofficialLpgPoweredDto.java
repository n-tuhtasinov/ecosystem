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
 * @created 28.04.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnofficialLpgPoweredDto extends UnofficialEquipmentAppealDto {

    @SkipDb
    private String capacity;

    @SkipDb
    private String pressure;

    @SkipDb
    private String fuel;

    @SkipDb
    private String gasSupplyProjectPath;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.LPG_POWERED;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_LPG_POWERED;
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
        super.getParameters().put("capacity", capacity);
        super.getParameters().put("pressure", pressure);
        super.getParameters().put("fuel", fuel);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        super.getFiles().put("gasSupplyProjectPath", gasSupplyProjectPath); // add file the map
        return true;
    }
}
