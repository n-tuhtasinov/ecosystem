package uz.technocorp.ecosystem.modules.equipmentappeal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
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
public class PipelineDto extends EquipmentAppealDto implements AppealDto {

    @NotNull(message = "Qurilmaning putur yetkazmaydigan nazoratda ko'rikdan o'tkazish sanasi jo'natilmadi")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nonDestructiveCheckDate;

    @NotBlank(message = "Diametr jo'natilmadi")
    private String diameter;

    @NotBlank(message = "Devor qalinligi jo'natilmadi")
    private String thickness;

    @NotBlank(message = "Uzunligi jo'natilmadi")
    private String length;

    @NotBlank(message = "Bosim jo'natilmadi")
    private String pressure;

    @NotBlank(message = "Muhit jo'natilmadi")
    private String environment;

    @Override
    public AppealType getAppealType() {
        return AppealType.REGISTER_PIPE;
    }

    @Override
    public LocalDate getDeadline() {
        return null;
    }
}
