package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;
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
public class UnofficialLpgContainerDto extends UnofficialEquipmentAppealDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nonDestructiveCheckDate;

    @SkipDb
    private String capacity;

    @SkipDb
    private String environment;

    @SkipDb
    private String pressure;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.LPG_CONTAINER;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_LPG_CONTAINER;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.UNOFFICIAL;
    }

    public void buildParameters() {
        super.getParameters().put("capacity", capacity);
        super.getParameters().put("environment", environment);
        super.getParameters().put("pressure", pressure);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
