package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.EquipmentAppealDto;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class IrsPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return IrsAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.IRS;
    }

    @Override
    protected String folderPath() {
        return "appeals/irs";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        IrsAppealDto dto = (IrsAppealDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getLegalName());
        parameters.put("legalTin", profile.getTin().toString());
        parameters.put("identifierType", dto.getIdentifierType());
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("serialNumber", dto.getSerialNumber());
        parameters.put("type", dto.getType());
        parameters.put("category", dto.getCategory());
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());
        parameters.put("address", dto.getAddress());
        parameters.put("fullName", profile.getFullName());

        return parameters;
    }
}