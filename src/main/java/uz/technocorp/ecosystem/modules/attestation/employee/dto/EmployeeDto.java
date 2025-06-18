package uz.technocorp.ecosystem.modules.attestation.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.attestation.employee.EmployeePosition;

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

    @NotBlank(message = "Pinfl yuborilmadi")
    private String pin;

    @NotBlank(message = "Ism sharif yuborilmadi")
    private String fullName;

    @NotNull(message = "Xodim lavozimi tanlanmadi")
    private EmployeePosition position;

    private String certNumber;
    private LocalDate certDate;
    private LocalDate certExpiryDate;
}
