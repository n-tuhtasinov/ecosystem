package uz.technocorp.ecosystem.modules.equipmentappeal.register.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
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
public class HeatPipelineDto extends EquipmentAppealDto {

    @SkipDb
    @NotBlank(message = "Diametr jo'natilmadi")
    private String diameter;

    @SkipDb
    @NotBlank(message = "Devor qalinligi jo'natilmadi")
    private String thickness;

    @SkipDb
    @NotBlank(message = "Uzunligi jo'natilmadi")
    private String length;

    @SkipDb
    @NotBlank(message = "Bosim jo'natilmadi")
    private String pressure;

    @SkipDb
    @NotBlank(message = "Temperatura jo'natilmadi")
    private String temperature;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.HEAT_PIPELINE;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_HEAT_PIPELINE;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    @Override
    public RegistrationMode getMode() {
        return RegistrationMode.OFFICIAL;
    }

    public void buildParameters() {
        super.getParameters().put("diameter", diameter);
        super.getParameters().put("thickness", thickness);
        super.getParameters().put("length", length);
        super.getParameters().put("pressure", pressure);
        super.getParameters().put("temperature", temperature);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
