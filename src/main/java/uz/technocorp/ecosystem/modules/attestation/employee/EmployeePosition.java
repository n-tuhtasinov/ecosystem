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

    LEADER("Raxbar"),
    TECHNICIAN("Injener, Texnik xodim"),
    EMPLOYEE("Oddiy xodim");

    public final String value;
}
