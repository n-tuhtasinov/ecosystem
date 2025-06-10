package uz.technocorp.ecosystem.modules.equipment;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRegistryDto;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface EquipmentService {
    void create(@Valid EquipmentRegistryDto dto);
    Page<HfPageView> getAllAttractionForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId);
    Page<HfPageView> getAllElevatorForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId);

}
