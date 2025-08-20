package uz.technocorp.ecosystem.modules.statistics.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.OwnerType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 19.08.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppealStatusFilterDto {

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Jismoniy shaxs yoki yuridik shaxs tanlanmadi")
    private OwnerType ownerType;
}
