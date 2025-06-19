package uz.technocorp.ecosystem.modules.attestation.employee;

import lombok.AllArgsConstructor;

/**
 * @author Suxrob
 * @version 1.0
 * @created 14.06.2025
 * @since v1.0
 */
@AllArgsConstructor
public enum EmployeePosition {

    LEADER(1, "Raxbar"),
    TECHNICIAN(2, "Injener, Texnik xodim"),
    EMPLOYEE(2, "Oddiy xodim");

    public final Integer direction; // Committee - 1  | Regional - 2
    public final String value;
}
