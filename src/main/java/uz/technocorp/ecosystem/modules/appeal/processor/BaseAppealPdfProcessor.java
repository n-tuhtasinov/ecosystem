package uz.technocorp.ecosystem.modules.appeal.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.template.Template;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.Map;
import java.util.UUID;

@Component
public abstract class BaseAppealPdfProcessor implements AppealPdfProcessor {

    @Autowired
    protected RegionService regionService;

    @Autowired
    protected DistrictService districtService;

    @Autowired
    protected ProfileService profileService;

    @Autowired
    protected TemplateService templateService;

    @Autowired
    protected AttachmentService attachmentService;

    // Abstract methods
    protected abstract TemplateType getTemplateType();

    protected abstract Map<String, String> buildParameters(AppealDto dto, Profile profile);

    protected abstract String folderPath();

    @Override
    public final String preparePdfWithParam(AppealDto dto, User user) {
        Template template = getTemplate(getTemplateType());
        Profile profile = getProfile(user.getProfileId());

        Map<String, String> parameters = buildParameters(dto, profile);
        String folderPath = folderPath();

        return attachmentService.createPdfFromHtml(template.getContent(), folderPath, parameters, true);
    }

    // General methods
    protected Region getRegion(Integer regionId) {
        return regionService.getById(regionId);
    }

    protected District getDistrict(Integer districtId) {
        return districtService.getDistrict(districtId);
    }

    protected Profile getProfile(UUID profileId) {
        return profileService.getProfile(profileId);
    }

    protected Template getTemplate(TemplateType type) {
        Template template = templateService.getByType(type.name());
        if (template == null) {
            throw new ResourceNotFoundException("Shablon", type.name(), "");
        }
        return template;
    }
}