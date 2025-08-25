package uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull(message = "Qurilmaning putur yetkazmaydigan nazoratda ko'rikdan o'tkazish sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nonDestructiveCheckDate;

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
    @NotBlank(message = "Muhit jo'natilmadi")
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
    public AppealMode getAppealMode() {
        return AppealMode.UNOFFICIAL;
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
