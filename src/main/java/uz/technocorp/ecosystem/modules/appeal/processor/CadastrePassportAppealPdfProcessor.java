package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.CadastrePassportDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class CadastrePassportAppealPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return CadastrePassportDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.CADASTRE_PASSPORT_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/cadastre-passport-appeals";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        CadastrePassportDto dto = (CadastrePassportDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("directorName", profile.getFullName());
        parameters.put("legalName", profile.getLegalName());
        parameters.put("legalTin", profile.getTin().toString());

        return parameters;
    }
}