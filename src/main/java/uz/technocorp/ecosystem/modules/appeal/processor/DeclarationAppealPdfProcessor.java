package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.declarationappeal.dto.DeclarationDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeclarationAppealPdfProcessor extends BaseAppealPdfProcessor {

    @Override
    public Class<? extends AppealDto> getSupportedType() {
        return DeclarationDto.class;
    }

    @Override
    protected TemplateType getTemplateType() {
        return TemplateType.DECLARATION_APPEAL;
    }

    @Override
    protected String folderPath() {
        return "appeals/declaration-appeals";
    }

    @Override
    protected Map<String, String> buildParameters(AppealDto appealDto, Profile profile) {
        DeclarationDto dto = (DeclarationDto) appealDto;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("directorName", profile.getDirectorName());
        parameters.put("legalName", profile.getName());
        parameters.put("legalTin", profile.getIdentity().toString());
        parameters.put("hfName", dto.getHfName());

        return parameters;
    }
}