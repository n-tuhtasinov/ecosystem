package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.equipment.enums.Sphere;

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
public class ElevatorDto extends EquipmentAppealDto implements AppealDto {

    @NotNull(message = "Soha tanlanmadi")
    private Sphere sphere;

    @NotBlank(message = "Yuk ko'tara olish qiymati jo'natilmadi")
    private String liftingCapacity;

    @NotBlank(message = "To'xtashlar soni jo'natilmadi")
    private String stopCount;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_ELEVATOR;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
