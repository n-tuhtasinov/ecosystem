package uz.technocorp.ecosystem.modules.equipmentappeal.register.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.shared.SkipDb;
import uz.technocorp.ecosystem.shared.dto.FileDto;

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
public class LpgPoweredDto extends EquipmentAppealDto {

    @SkipDb
    @NotBlank(message = "Hajm jo'natilmadi")
    private String capacity;

    @SkipDb
    @NotBlank(message = "Bosim jo'natilmadi")
    private String pressure;

    @SkipDb
    @NotBlank(message = "Yoqilg'i jo'natilmadi")
    private String fuel;

    @SkipDb
    @NotBlank(message = "Gaz ta'minoti loyihasi fayli uchun path jo'natilmadi")
    private String gasSupplyProjectPath;

    private EquipmentType type = EquipmentType.LPG_POWERED;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_LPG_POWERED;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    public void buildParameters() {
        super.getParameters().put("capacity", capacity);
        super.getParameters().put("pressure", pressure);
        super.getParameters().put("fuel", fuel);
    }

    @AssertTrue
    @Schema(hidden = true)
    public boolean isParametersBuilt() {
        buildParameters();
        super.getFiles().put("gasSupplyProjectPath", new FileDto(gasSupplyProjectPath, null)); // add file the map
        return true;
    }
}
