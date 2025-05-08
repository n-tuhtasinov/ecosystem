package uz.technocorp.ecosystem.modules.equipment;

import jakarta.validation.Valid;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRegistryDto;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface EquipmentService {
    void create(@Valid EquipmentRegistryDto dto);
}
