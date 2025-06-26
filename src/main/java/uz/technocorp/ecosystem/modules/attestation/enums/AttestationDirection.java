package uz.technocorp.ecosystem.modules.attestation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttestationDirection {
    COMMITTEE(1, "Committee"),
    REGIONAL(2, "Regional");

    private final Integer value;
    private final String description;

    public static AttestationDirection fromValue(Integer value) {
        for (AttestationDirection direction : values()) {
            if (direction.value.equals(value)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid direction value: " + value);
    }
}