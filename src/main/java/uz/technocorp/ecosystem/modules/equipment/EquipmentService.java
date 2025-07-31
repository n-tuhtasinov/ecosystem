package uz.technocorp.ecosystem.modules.equipment;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.view.AttractionPassportView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentRiskView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentViewById;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface EquipmentService {

    Page<EquipmentRiskView> getAllAttractionForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId);

    Page<EquipmentRiskView> getAllElevatorForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId);

    void create(Appeal appeal);

    Page<EquipmentView> getAll(User user, EquipmentParams params);

    EquipmentViewById getById(UUID equipmentId);

    Equipment findById(UUID equipmentId);

    AttractionPassportView getAttractionPassportByRegistryNumber(String registryNumber);

    EquipmentViewById findByRegistryNumber(String registryNumber);

    Long getCount(User user);

    String createEquipmentRegistryPdf(Appeal appeal, EquipmentDto dto, EquipmentInfoDto info, LocalDate registrationDate);

    List<Equipment> getAllEquipmentByTypeAndTinOrPin(Long tin, EquipmentType type);

}
