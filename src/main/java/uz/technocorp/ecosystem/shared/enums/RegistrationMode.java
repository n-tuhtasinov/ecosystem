package uz.technocorp.ecosystem.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 25.08.2025
 * @since v1.0
 */
@Getter
@AllArgsConstructor
public enum RegistrationMode {
    OFFICIAL("Rasmiy"),
    UNOFFICIAL("Norasmiy");

    private final String label;
}
