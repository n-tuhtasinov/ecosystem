package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 24.04.2025
 * @since v1.0
 */
public class BoilerDto extends EquipmentAppealDto implements AppealDto {

    @NotNull(message = "Qurilmaning putur yetkazmaydigan nazoratda ko'rikdan o'tkazish sanasi jo'natilmadi")
    private LocalDate nonDestructiveCheckDate;

    @NotBlank(message = "Hajm jo'natilmadi")
    private String capacity;

    @NotBlank(message = "Muhit jo'natilmadi")
    private String environment;

    @NotBlank(message = "Ruxsat etilgan bosim jo'natilmadi")
    private String pressure;


    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_BOILER;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
