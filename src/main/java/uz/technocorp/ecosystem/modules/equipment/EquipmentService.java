package uz.technocorp.ecosystem.modules.equipment;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRegistryDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRiskParamsDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipment.view.*;
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

    Page<EquipmentRiskView> getAllEquipmentRiskAssessment(EquipmentRiskParamsDto paramsDto);

    void create(Appeal appeal);

    Page<EquipmentView> getAll(User user, EquipmentParams params);

    EquipmentViewById getById(UUID equipmentId);

    Equipment findById(UUID equipmentId);

    AttractionPassportView getAttractionPassportByRegistryNumber(String registryNumber);

    EquipmentViewById findByRegistryNumber(String registryNumber);

    Long getCount(User user);

    String createEquipmentRegistryPdf(Appeal appeal, EquipmentRegistryDto dto);

    List<Equipment> getAllEquipmentByTypeAndTinOrPin(Long tin, EquipmentType type);

    Equipment findByRegistryNumberAndTypeAndActive(String registryNumber, EquipmentType type, Boolean isActive);

    Equipment findByRegistryNumberAndTypeAndOwnerAndActive(String registryNumber, Long ownerIdentity, EquipmentType type, Boolean active);

    void deactivateEquipment(Appeal appeal);

    void reRegister(Appeal appeal);

    EquipmentCountByStatusView countEquipmentStatusByDateAndRegionId(LocalDate date, Integer regionId);
}
