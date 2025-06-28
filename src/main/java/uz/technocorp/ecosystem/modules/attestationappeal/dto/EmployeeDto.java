package uz.technocorp.ecosystem.modules.attestationappeal.dto;

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
public class EmployeeDto {

    @NotBlank(message = "Pinfl kiritilmadi")
    private String pin;

    @NotBlank(message = "Ism sharif yuborilmadi")
    private String fullName;

    @NotBlank(message = "Mutaxassislik yuborilmadi")
    private String profession;

    @NotNull(message = "Xodim darajasi tanlanmadi")
    private EmployeeLevel level;

    @NotBlank(message = "Sertifikat raqami kiritilmadi")
    private String certNumber;

    @NotNull(message = "Sertifikat sanasi kiritilmadi")
    private LocalDate certDate;

    @NotNull(message = "Sertifikat muddati kiritilmadi")
    private LocalDate certExpiryDate;

    @NotNull(message = "Kontexnazorato'quv DM o'qigan muddati boshlanishi kiritilmadi")
    private LocalDate ctcTrainingFromDate;

    @NotNull(message = "Kontexnazorato'quv DM o'qigan muddati tugashi kiritilmadi")
    private LocalDate ctcTrainingToDate;

    private LocalDate dateOfEmployment;
}
