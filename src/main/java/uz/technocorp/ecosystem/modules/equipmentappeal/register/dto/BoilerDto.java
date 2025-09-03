package uz.technocorp.ecosystem.modules.equipmentappeal.register.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
 * @created 24.04.2025
 * @since v1.0
 */
@NoArgsConstructor
@Setter
@Getter
public class BoilerDto extends EquipmentAppealDto {

    @NotNull(message = "Qurilmaning putur yetkazmaydigan nazoratda ko'rikdan o'tkazish sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nonDestructiveCheckDate;

    @SkipDb
    @NotBlank(message = "Hajm jo'natilmadi")
    private String capacity;

    @SkipDb
    @NotBlank(message = "Muhit jo'natilmadi")
    private String environment;

    @SkipDb
    @NotBlank(message = "Ruxsat etilgan bosim jo'natilmadi")
    private String pressure;

    private EquipmentType type = EquipmentType.BOILER;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_BOILER;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    public void buildParameters() {
        super.getParameters().put("capacity", capacity);
        super.getParameters().put("environment", environment);
        super.getParameters().put("pressure", pressure);
    }

    @AssertTrue
    @Schema(hidden = true)
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
