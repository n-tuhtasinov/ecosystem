package uz.technocorp.ecosystem.modules.report.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.enums.OwnerType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class AppealTypeFilterDto {

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Jismoniy shaxs yoki yuridik shaxs tanlanmadi")
    private OwnerType ownerType;

    List<AppealType> appealTypes = new ArrayList<>();
}
