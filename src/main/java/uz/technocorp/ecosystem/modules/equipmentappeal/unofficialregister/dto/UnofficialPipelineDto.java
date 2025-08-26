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
public class UnofficialPipelineDto extends UnofficialEquipmentAppealDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nonDestructiveCheckDate;

    @SkipDb
    private String diameter;

    @SkipDb
    private String thickness;

    @SkipDb
    private String length;

    @SkipDb
    private String pressure;

    @SkipDb
    private String environment;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.PIPELINE;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_PIPELINE;
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
        super.getParameters().put("diameter", diameter);
        super.getParameters().put("thickness", thickness);
        super.getParameters().put("length", length);
        super.getParameters().put("pressure", pressure);
        super.getParameters().put("environment", environment);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
