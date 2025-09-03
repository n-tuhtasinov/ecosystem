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
public class CraneDto extends EquipmentAppealDto {

    @SkipDb
    @NotBlank(message = "Strelasining uzunligi jo'natilmadi")
    private String boomLength;

    @SkipDb
    @NotBlank(message = "Yuk ko'tara olish qiymati jo'natilmadi")
    private String liftingCapacity;

    private EquipmentType type = EquipmentType.CRANE;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_CRANE;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    public void buildParameters() {
        super.getParameters().put("boomLength", boomLength);
        super.getParameters().put("liftingCapacity", liftingCapacity);
    }

    @AssertTrue
    @Schema(hidden = true)
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }

}
