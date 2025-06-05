package uz.technocorp.ecosystem.modules.equipment.enums;

import lombok.AllArgsConstructor;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 14.03.2025
 * @since v1.0
 * @description  the levels of risk for only ride (attraction)
 */
@AllArgsConstructor
public enum RiskLevel {
    I("1"),
    II("2"),
    III("3"),
    IV("4");

    public final String value;
}
