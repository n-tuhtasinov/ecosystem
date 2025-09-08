package uz.technocorp.ecosystem.modules.equipment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 13.03.2025
 * @since v1.0
 * @description the sphere for only lift
 */
@Getter
@AllArgsConstructor
public enum Sphere {
    RESIDENTIAL("Turar-joy binosi"),
    HOTEL("Mehmonxona"),
    SOCIAL("Ijtimoiy soha"),
    INDUSTRIAL("Sanoat korxonasi"),
    SERVICE("Xizmat ko'rsatish obyekti"),
    OTHER("Boshqa obyektlar");

    private final String label;
}
