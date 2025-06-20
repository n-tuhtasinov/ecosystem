package uz.technocorp.ecosystem.modules.attestation.employee;

import lombok.AllArgsConstructor;

/**
 * @author Suxrob
 * @version 1.0
 * @created 14.06.2025
 * @since v1.0
 */
@AllArgsConstructor
public enum EmployeeLevel {

    LEADER(1, "Raxbar", 5),
    TECHNICIAN(2, "Injener, Texnik xodim", 3),
    EMPLOYEE(2, "Oddiy xodim", 1);

    public final Integer direction; // Committee - 1  | Regional - 2
    public final String value;
    public final Integer term; // Attestatsiya muddati
}
