package uz.technocorp.ecosystem.modules.equipmentappeal.register.dto;

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
 * @created 25.04.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CablewayDto extends EquipmentAppealDto {

    @NotNull(message = "Qurilmaning putur yetkazmaydigan nazoratda ko'rikdan o'tkazish sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nonDestructiveCheckDate;

    @SkipDb
    @NotBlank(message = "Tezlik jo'natilmadi")
    private String speed;

    @SkipDb
    @NotBlank(message = "Harakatlanuvchi sostav soni jo'natilamdi")
    private String passengerCount;

    @SkipDb
    @NotBlank(message = "Uzunligi jo'natilmadi")
    private String length;

    @Schema(hidden = true)
    private EquipmentType type = EquipmentType.CABLEWAY;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_CABLEWAY;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }

    public void buildParameters() {
        super.getParameters().put("speed", speed);
        super.getParameters().put("passengerCount", passengerCount);
        super.getParameters().put("length", length);
    }

    @AssertTrue
    public boolean isParametersBuilt() {
        buildParameters();
        return true;
    }
}
