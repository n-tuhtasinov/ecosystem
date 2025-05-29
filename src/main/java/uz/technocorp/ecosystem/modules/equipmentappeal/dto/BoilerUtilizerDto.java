package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

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
public class BoilerUtilizerDto extends EquipmentAppealDto {

    @NotNull(message = "Qurilmaning putur yetkazmaydigan nazoratda ko'rikdan o'tkazish sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nonDestructiveCheckDate;

    @NotBlank(message = "Hajm jo'natilmadi")
    private String capacity;

    @NotBlank(message = "Muhit jo'natilmadi")
    private String environment;

    @NotBlank(message = "Ruxsat etilgan bosim jo'natilmadi")
    private String pressure;

    @NotBlank(message = "Zichlik jo'natilmadi")
    private String density;

    @NotBlank(message = "Temperatura jo'natilmadi")
    private String temperature;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_BOILER_UTILIZER;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
