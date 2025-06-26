package uz.technocorp.ecosystem.modules.attestation.view;

import lombok.*;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 23.06.2025
 * @since v1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttestationView {

    private String employeePin;
    private String employeeName;
    private EmployeeLevel employeeLevel;
    private LocalDate expiryDate;
    private UUID appealId;
    private Long legalTin;
    private String legalName;
    private String legalAddress;
    private String hfName;
    private String hfAddress;
    private AttestationStatus status;
}
