package uz.technocorp.ecosystem.modules.appeal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author Suxrob
 * @version 1.0
 * @created 28.07.2025
 * @since v1.0
 */
@Getter
@AllArgsConstructor
public enum OwnerType {

    LEGAL(9, "Yuridik shaxs"),
    INDIVIDUAL(14, "Jismoniy shaxs");

    private final Integer identityLength;
    private final String label;

    public static OwnerType find(Integer identityLength) {
        return Stream.of(OwnerType.values())
                .filter(a -> identityLength != null && identityLength.equals(a.getIdentityLength()))
                .findFirst().orElse(null);
    }
}
