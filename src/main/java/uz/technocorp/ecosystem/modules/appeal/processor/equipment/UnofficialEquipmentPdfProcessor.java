package uz.technocorp.ecosystem.modules.appeal.processor.equipment;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.processor.BaseAppealPdfProcessor;
import uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto.UnofficialEquipmentAppealDto;
import uz.technocorp.ecosystem.modules.office.projection.OfficeViewById;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UnofficialEquipmentPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return UnofficialEquipmentAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.UNOFFICIAL_EQUIPMENT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/equipment/unofficial";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile inspectorProfile) {
        UnofficialEquipmentAppealDto dto = (UnofficialEquipmentAppealDto) appealDto;

        User user = getOrCreateByIdentityAndDate(dto.getIdentity(), dto.getBirthDate());
        Profile legalOrIndividual = getProfile(user.getProfileId());

        OfficeViewById office = officeService.getById(inspectorProfile.getOfficeId());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("officeName", office.getName());
        parameters.put("legalName", legalOrIndividual.getName());
        parameters.put("legalTin", legalOrIndividual.getIdentity().toString());
        parameters.put("facilityName", dto.getHazardousFacilityName() != null ? dto.getHazardousFacilityName() + "ga tegishli" : "");
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());
        parameters.put("address", dto.getAddress());
        parameters.put("equipmentType", dto.getType().value);
        parameters.put("inspectorName", inspectorProfile.getName());

        return parameters;
    }
}