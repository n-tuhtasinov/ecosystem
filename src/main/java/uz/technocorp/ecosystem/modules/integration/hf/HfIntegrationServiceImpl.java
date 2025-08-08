package uz.technocorp.ecosystem.modules.integration.hf;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.hf.enums.HFSphere;
import uz.technocorp.ecosystem.modules.hftype.HfTypeService;
import uz.technocorp.ecosystem.modules.integration.equipment.dto.InfoDto;
import uz.technocorp.ecosystem.modules.integration.hf.dto.HfInfoDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.RegionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Suxrob
 * @version 1.0
 * @created 08.08.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class HfIntegrationServiceImpl implements HfIntegrationService {

    @Value("${app.file-base.url}")
    private String fileBaseUrl;

    private final HazardousFacilityService hfService;
    private final DistrictService districtService;
    private final ProfileService profileService;
    private final HfTypeService hfTypeService;
    private final RegionService regionService;

    @Override
    public InfoDto<HfInfoDto> getHfInfo(String tin) {
        if (tin == null || tin.length() != 9) {
            throw new CustomException("Bunday STIR bo'yicha  ma'lumotida topilmadi");
        }

        Profile profile = profileService.findByIdentity(Long.parseLong(tin));

        List<HazardousFacility> hfList = new ArrayList<>(hfService.getAllByTin(profile.getIdentity(), true));

        InfoDto<HfInfoDto> info = new InfoDto<>();

        info.setTinOrPin(profile.getIdentity());
        info.setLegalName(profile.getName());
        info.setLegalAddress(profile.getAddress());
        info.setFullName(profile.getDirectorName());
        info.setRegionName(profile.getRegionName());
        info.setDistrictName(profile.getDistrictName());
        info.setPhoneNumber(profile.getPhoneNumber());

        info.setHf(hfList.stream().map(this::map).toList());

        return info;
    }

    // MAPPER
    private HfInfoDto map(HazardousFacility hf) {
        HfInfoDto dto = new HfInfoDto();

        dto.setName(hf.getName());
        dto.setRegistrationDate(hf.getRegistrationDate());
        dto.setRegistryNumber(hf.getRegistryNumber());
        dto.setRegionName(regionService.findById(hf.getRegionId()).getName());
        dto.setDistrictName(districtService.findById(hf.getDistrictId()).getName());
        dto.setAddress(hf.getAddress());
        dto.setUpperOrganization(hf.getUpperOrganization());
        dto.setLocation(hf.getLocation());
        dto.setHazardousSubstance(hf.getHazardousSubstance());
        dto.setHfTypeName(hf.getHfTypeId() != null ? hfTypeService.getById(hf.getHfTypeId()).getName() : null);
        dto.setExtraArea(hf.getExtraArea());
        dto.setDescription(hf.getDescription());
        dto.setSpheres(hf.getSpheres().stream().map(HFSphere::getLabel).toList());
        dto.setPeriodicUpdateReason(hf.getPeriodicUpdateReason());
        dto.setPeriodicUpdateFilePath(hf.getPeriodicUpdateFilePath() != null ? fileBaseUrl + hf.getPeriodicUpdateFilePath() : null);
        dto.setActive(hf.isActive());
        dto.setRegistryFilePath(fileBaseUrl + hf.getRegistryFilePath());

        hf.getFiles().replaceAll((key, path) -> path != null ? fileBaseUrl + path : null);
        dto.setFiles(hf.getFiles());

        return dto;
    }
}
