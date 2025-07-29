package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.AttractionPassportDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AttractionPassportPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return AttractionPassportDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.ATTRACTION_PASSPORT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/equipment";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        AttractionPassportDto dto = (AttractionPassportDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getName());
        parameters.put("legalTin", profile.getIdentity().toString());
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("manufacturedAt", dto.getManufacturedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        parameters.put("address", dto.getAddress());
        parameters.put("attractionName", dto.getAttractionName());
        parameters.put("childEquipmentName", dto.getChildEquipmentName());
        parameters.put("childEquipmentSortName", dto.getChildEquipmentSortName());
        parameters.put("fullName", profile.getDirectorName());

        return parameters;
    }
}