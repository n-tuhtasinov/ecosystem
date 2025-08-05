package uz.technocorp.ecosystem.modules.equipmentappeal.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.childequipmentsort.ChildEquipmentSortService;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.AttractionDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.AttractionPassportDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.EquipmentAppealDto;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 14.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class EquipmentAppealServiceImpl implements EquipmentAppealService {

    private final HazardousFacilityService hfService;
    private final ChildEquipmentService childEquipmentService;
    private final ChildEquipmentSortService childEquipmentSortService;
    private final EquipmentService equipmentService;

    @Override
    public void setHfNameAndChildEquipmentName(EquipmentAppealDto dto) {
        if (dto.getHazardousFacilityId() != null) {
            String hfName = hfService.getHfNameById(dto.getHazardousFacilityId());
            dto.setHazardousFacilityName(hfName);
        }
        String name = childEquipmentService.getNameById(dto.getChildEquipmentId());
        dto.setChildEquipmentName(name);
    }

    @Override
    public void setChildEquipmentNameAndChildEquipmentSortName(AttractionPassportDto dto) {
        String childEquipmentName = childEquipmentService.getNameById(dto.getChildEquipmentId());
        String childEquipmentSortName = childEquipmentSortService.getNameById(dto.getChildEquipmentSortId());
        dto.setChildEquipmentName(childEquipmentName);
        dto.setChildEquipmentSortName(childEquipmentSortName);
    }

    @Override
    public void setRequiredFields(AttractionDto dto) {
        // find attraction passport by id
        Equipment passport = equipmentService.findById(dto.getAttractionPassportId());

        dto.setAttractionPassportRegistryNumber(passport.getRegistryNumber());
        dto.setAttractionName(dto.getAttractionName());
        dto.setChildEquipmentId(dto.getChildEquipmentId());
        dto.setChildEquipmentName(dto.getChildEquipmentName());
        dto.setChildEquipmentSortId(dto.getChildEquipmentSortId());
        dto.setChildEquipmentSortName(dto.getChildEquipmentSortName());
        dto.setManufacturedAt(passport.getManufacturedAt());
        dto.setAcceptedAt(passport.getAcceptedAt());
        dto.setFactoryNumber(passport.getFactoryNumber());
        dto.setCountry(passport.getCountry());
        dto.setRiskLevel(passport.getRiskLevel());
    }
}
