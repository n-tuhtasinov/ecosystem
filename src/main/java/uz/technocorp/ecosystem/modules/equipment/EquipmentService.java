package uz.technocorp.ecosystem.modules.equipment;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.view.AttractionPassportView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentViewById;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface EquipmentService {

    Page<HfPageView> getAllAttractionForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId);

    Page<HfPageView> getAllElevatorForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId);

    void create(Appeal appeal);

    Page<EquipmentView> getAll(User user, EquipmentParams params);

    EquipmentViewById getById(UUID equipmentId);

    Equipment findById(UUID equipmentId);

    AttractionPassportView getAttractionPassportByRegistryNumber(String registryNumber);

    Equipment findByRegistryNumber(String oldEquipmentRegistryNumber);
}
