package uz.technocorp.ecosystem.modules.integration.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.integration.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.integration.equipment.dto.InfoDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.RegionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Suxrob
 * @version 1.0
 * @created 21.07.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class EquipmentIntegrationServiceImpl implements EquipmentIntegrationService {

    @Value("${app.file-base.url}")
    private String fileBaseUrl;

    private final ChildEquipmentService childEquipmentService;
    private final EquipmentService equipmentService;
    private final DistrictService districtService;
    private final ProfileService profileService;
    private final RegionService regionService;

    @Override
    public InfoDto<EquipmentInfoDto> getEquipmentInfo(String tinOrPin, EquipmentType type) {

        if (tinOrPin == null || (tinOrPin.length() != 9 && tinOrPin.length() != 14)) {
            throw new CustomException("STIR yoki Pinfl ma'lumotida xatolik");
        }

        Profile profile = profileService.findByIdentity(Long.parseLong(tinOrPin));

        List<Equipment> equipmentList = new ArrayList<>(equipmentService.getAllEquipmentByTypeAndTinOrPin(profile.getIdentity(), type));

        InfoDto<EquipmentInfoDto> info = new InfoDto<>();

        info.setTinOrPin(profile.getIdentity());
        info.setLegalName(profile.getName());
        info.setLegalAddress(profile.getAddress());
        info.setFullName(profile.getDirectorName());
        info.setRegionName(profile.getRegionName());
        info.setDistrictName(profile.getDistrictName());
        info.setPhoneNumber(profile.getPhoneNumber());

        info.setEquipment(equipmentList.stream().map(this::map).toList());

        return info;
    }

    private EquipmentInfoDto map(Equipment eq) {
        EquipmentInfoDto dto = new EquipmentInfoDto();

        dto.setType(eq.getType().value);
        dto.setChildEquipmentName(childEquipmentService.getNameById(eq.getChildEquipmentId()));
        dto.setRegistryNumber(eq.getRegistryNumber());
        dto.setFactory(eq.getFactory());
        dto.setFactoryNumber(eq.getFactoryNumber());
        dto.setRegionName(regionService.findById(eq.getRegionId()).getName());
        dto.setDistrictName(districtService.findById(eq.getDistrictId()).getName());
        dto.setAddress(eq.getAddress());
        dto.setModel(eq.getModel());
        dto.setLocation(eq.getLocation());
        dto.setManufacturedAt(eq.getManufacturedAt());
        dto.setPartialCheckDate(eq.getPartialCheckDate());
        dto.setFullCheckDate(eq.getFullCheckDate());
        dto.setRegistrationDate(eq.getRegistrationDate());
        dto.setRegistryFilePath(fileBaseUrl + eq.getRegistryFilePath());
        dto.setIsActive(eq.getIsActive());
        dto.setParameters(eq.getParameters());

        return dto;
    }
}
