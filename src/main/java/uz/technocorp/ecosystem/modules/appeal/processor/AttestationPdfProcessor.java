package uz.technocorp.ecosystem.modules.appeal.processor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.attestationappeal.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationDirection;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
@Component
public class AttestationPdfProcessor extends BaseAppealPdfProcessor {

    protected TemplateType templateType;

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return AttestationDto.class;
    }

    @Override
    protected String folderPath() {
        return "appeals/attestation";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        AttestationDto dto = (AttestationDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", profile.getLegalName());
        parameters.put("directorName", profile.getFullName());

        if (AttestationDirection.COMMITTEE.equals(dto.getDirection())) {
            parameters.put("employees", dto.getDynamicRows());

            templateType = TemplateType.ATTESTATION_LEADER_APPEAL;

        } else {
            String[] split = dto.getDateOfAttestation().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.of("uz"))).split(" ");
            parameters.put("year", split[0]);
            parameters.put("month", split[1]);
            parameters.put("day", split[2]);
            parameters.put("time", split[3]);
            parameters.put("regionName", getRegion(dto.getRegionId()).getName());
            parameters.put("districtName", getDistrict(dto.getDistrictId()).getName());
            parameters.put("address", dto.getAddress());

            templateType = TemplateType.ATTESTATION_EMPLOYEE_APPEAL;
        }
        return parameters;
    }
}