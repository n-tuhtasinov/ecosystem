package uz.technocorp.ecosystem.modules.employee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDeleteDto {

    @NotNull(message = "XICHO tanlanmadi")
    private UUID hfId;

    @NotNull(message = "Xodimlar tanlanmadi")
    private List<String> pinList;
}
