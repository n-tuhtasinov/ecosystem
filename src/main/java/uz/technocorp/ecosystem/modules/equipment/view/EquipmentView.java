package uz.technocorp.ecosystem.modules.equipment.view;

import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
public record EquipmentView (
        UUID id,
        LocalDate registrationDate,
        String registryNumber,
        EquipmentType type,
        String ownerName,
        Long ownerIdentity,
        String ownerAddress,
        String hfName,
        String address,
        String factoryNumber
) {}
