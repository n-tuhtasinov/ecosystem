package uz.technocorp.ecosystem.modules.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;

import java.time.LocalDate;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeAddDto {

    @NotBlank(message = "Pinfl kiritilmadi")
    private String pin;

    @NotBlank(message = "Ism sharif yuborilmadi")
    private String fullName;

    @NotBlank(message = "Mutaxassislik yuborilmadi")
    private String profession;

    @NotNull(message = "Xodim darajasi tanlanmadi")
    private EmployeeLevel level;

    private String certNumber;
    private LocalDate certDate;
    private LocalDate certExpiryDate;
    private LocalDate ctcTrainingFromDate;
    private LocalDate ctcTrainingToDate;
    private LocalDate dateOfEmployment;
}
