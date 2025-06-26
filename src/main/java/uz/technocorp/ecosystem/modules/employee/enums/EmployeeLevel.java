package uz.technocorp.ecosystem.modules.employee.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Suxrob
 * @version 1.0
 * @created 14.06.2025
 * @since v1.0
 */
@Getter
@AllArgsConstructor
public enum EmployeeLevel {

    LEADER(1, "Raxbar", 5),
    TECHNICIAN(2, "Injener, Texnik xodim", 3),
    EMPLOYEE(2, "Oddiy xodim", 1);

    private final Integer direction; // Committee - 1  | Regional - 2
    private final String value;
    private final Integer term; // Attestatsiya muddati
}
