package uz.technocorp.ecosystem.modules.employee.view;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeView {

    private UUID id;
    private String pin;
    private String name;
    private String profession;
    private String certNumber;
    private LocalDate dateOfEmployment;
    private LocalDate certDate;
    private LocalDate certExpiryDate;
    private LocalDate ctcTrainingFromDate;
    private LocalDate ctcTrainingToDate;
    private EmployeeLevel level;
    private String hfName;


}
