package uz.technocorp.ecosystem.modules.equipment.enums;


import lombok.AllArgsConstructor;

import java.util.stream.Stream;

/**
 * @author Suxrob
 * @version 1.0
 * @created 11.06.2025
 * @since v1.0
 */
@AllArgsConstructor
public enum EquipmentParameter {

    BOOM_LENGTH("boomLength", "Strela uzunligi", "metr"),
    CAPACITY("capacity", "Sig'imi", "m³"),
    DENSITY("density", "Zichlik", "kg/m³"),
    DIAMETER("diameter", "Diametr", "mm"),
    ENVIRONMENT("environment", "Muhit", ""),
    FUEL("fuel", "Yoqilg'i", "gaz"),
    HEIGHT("height", "Balandlik", "metr"),
    LENGTH("length", "Uzunlik", "metr"),
    LIFTING_CAPACITY("liftingCapacity", "Ko'tarish sig'imi", "kg"),
    PASSENGER_COUNT("passengerCount", "Yo'lovchilar soni", ""),
    PASSENGERS_PER_MINUTE("passengersPerMinute", "Daqiqadagi yo'lovchilar soni", "odam/minut"),
    PRESSURE("pressure", "Bosim", "MPa"),
    SPEED("speed", "Tezlik", ""),
    STOP_COUNT("stopCount", "To'xtashlar soni", ""),
    TEMPERATURE("temperature", "Harorat", "°C"),
    THICKNESS("thickness", "Qalinlik", "mm");

    public final String id;
    public final String nameUz;
    public final String unit;

    public static EquipmentParameter find(String id) {
        return Stream.of(EquipmentParameter.values())
                .filter(e -> id != null && id.equals(e.id))
                .findFirst().orElse(null);
    }

    public static String nameWithUnit(String id) {
        return Stream.of(EquipmentParameter.values())
                .filter(e -> id != null && id.equals(e.id))
                .map(e -> e.nameUz + (e.unit.isBlank() ? "" : " (" + e.unit + ")"))
                .findFirst()
                .orElse(null);
    }
}
