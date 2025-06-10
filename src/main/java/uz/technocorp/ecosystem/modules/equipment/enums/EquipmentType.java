package uz.technocorp.ecosystem.modules.equipment.enums;

import lombok.AllArgsConstructor;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 12.03.2025
 * @since v1.0
 */
@AllArgsConstructor
public enum EquipmentType {
    CRANE("Kran"),
    CONTAINER("Bosim ostida ishlovchi idish"),
    BOILER("Bug'qozon"),
    ELEVATOR("Lift"),
    ESCALATOR("Eskalator"),
    CABLEWAY("Osma arqonli yuruvchi yo'l"),
    HOIST("Yuk ko'targich"),
    PIPELINE("Quvur"),
    ATTRACTION_PASSPORT("Attraksion pasporti"),
    ATTRACTION("Atraksion"),
    CHEMICAL_CONTAINER("Bosim ostida ishlovchi idish(kimyoviy)"),
    HEAT_PIPELINE("Bug' va issiq suv quvurlari"),
    BOILER_UTILIZER("Qozon utilizator"),
    LPG_CONTAINER("Bosim ostida ishlovchi idishlar (suyiltirilgan uglevodor gazi uchun)"),
    LPG_POWERED("Uglevodorod gazi ishlatadigan qurilmalar");

    public final String value;
}
