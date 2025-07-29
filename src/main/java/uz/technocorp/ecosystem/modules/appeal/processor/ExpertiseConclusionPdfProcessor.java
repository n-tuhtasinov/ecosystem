package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationSphere;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpConclusionAppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExpertiseConclusionPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return ExpConclusionAppealDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.EXPERTISE_CONCLUSION_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/accreditation";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        ExpConclusionAppealDto dto = (ExpConclusionAppealDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getName());
        parameters.put("customerLegalAddress", dto.getCustomerLegalAddress());
        parameters.put("customerLegalName", dto.getCustomerLegalName());
        parameters.put("customerTin", dto.getCustomerTin().toString());

        parameters.put("director", profile.getDirectorName());
        parameters.put("legalAddress", profile.getAddress());
        parameters.put("phoneNumber", dto.getPhoneNumber());

        parameters.put("certificateDate", getFormattedDateAsString(dto.getCertificateDate()));
        parameters.put("accreditationSpheres", dto.getAccreditationSpheres().stream().map(AccreditationSphere::name).collect(Collectors.joining(", ")));
        parameters.put("certificateValidityDate", getFormattedDateAsString(dto.getCertificateValidityDate()));
        parameters.put("certificateNumber", dto.getCertificateNumber());

        parameters.put("objectName", dto.getObjectName());
        parameters.put("firstSymbolsGroup", dto.getFirstSymbolsGroup());
        parameters.put("secondSymbolsGroup", dto.getSecondSymbolsGroup());
        parameters.put("thirdSymbolsGroup", dto.getThirdSymbolsGroup());
        parameters.put("regionName", getRegion(dto.getRegionId()).getName());
        parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());
        parameters.put("objectAddress", dto.getAddress());

        return parameters;
    }
}