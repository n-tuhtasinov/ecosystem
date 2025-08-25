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
public class UnofficialBoilerUtilizerDto extends UnofficialEquipmentAppealDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nonDestructiveCheckDate;

    @SkipDb
    private String capacity;

    @SkipDb
    private String environment;

    @SkipDb
    private String pressure;

    @SkipDb
    private String density;

    @SkipDb
    private String temperature;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.BOILER_UTILIZER;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_BOILER_UTILIZER;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    public void buildParameters() {
        super.getParameters().put("capacity", capacity);
        super.getParameters().put("environment", environment);
        super.getParameters().put("pressure", pressure);
        super.getParameters().put("density", density);
        super.getParameters().put("temperature", temperature);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
