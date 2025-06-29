package uz.technocorp.ecosystem.modules.cadastreappeal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.ConfirmCadastreDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.RejectCadastreDto;
import uz.technocorp.ecosystem.modules.department.DepartmentService;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.template.Template;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class CadastreAppealServiceImpl implements CadastreAppealService {


    private final AppealService appealService;
    private final TemplateService templateService;
    private final ProfileService profileService;
    private final OfficeService officeService;
    private final DepartmentService departmentService;
    private final AttachmentService attachmentService;

    @Override
    public String generateConfirmationPdf(User user, ConfirmCadastreDto confirmCadastreDto) {
        Appeal appeal = appealService.findById(confirmCadastreDto.appealId());

        Template template;
        if (appeal.getAppealType() == AppealType.REGISTER_CADASTRE_PASSPORT){
            template = templateService.getByType(TemplateType.REPLY_ACCEPT_CADASTRE_PASSPORT_APPEAL.name());
        }else {
            template = templateService.getByType(TemplateType.REPLY_ACCEPT_DECLARATION_APPEAL.name());
        }

        String[] appealFormattedDate = getSplitDate(appeal.getCreatedAt());
        String appealDate = appealFormattedDate[0] + " yil " + appealFormattedDate[2] + " " + appealFormattedDate[1];

        String[] confirmFormattedDate = getSplitDate(LocalDateTime.now());
        String confirmationDate = confirmFormattedDate[0] + " yil " + confirmFormattedDate[2] + " " + confirmFormattedDate[1];

        String[] workSpace = getExecutorWorkspace(user);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("date", appealDate);
        parameters.put("appealNumber", appeal.getNumber());
        parameters.put("executorWorkspace", workSpace[0]);
        parameters.put("confirmationDate", confirmationDate);
        parameters.put("registryNumber", confirmCadastreDto.registryNumber());
        parameters.put("executorFullWorkspace", workSpace[0] + " " + workSpace[1]);
        parameters.put("executorName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/confirm", parameters, true);
    }

    @Override
    public String generateRejectionPdf(User user, RejectCadastreDto rejectCadastreDto) {

        Appeal appeal = appealService.findById(rejectCadastreDto.appealId());

        Template template;
        if (appeal.getAppealType() == AppealType.REGISTER_CADASTRE_PASSPORT){
            template = templateService.getByType(TemplateType.REPLY_REJECT_CADASTRE_PASSPORT_APPEAL.name());
        }else {
            template = templateService.getByType(TemplateType.REPLY_REJECT_DECLARATION_APPEAL.name());
        }

        String[] appealFormattedDate = getSplitDate(appeal.getCreatedAt());
        String appealDate = appealFormattedDate[0] + " yil " + appealFormattedDate[2] + " " + appealFormattedDate[1];

        String[] workSpace = getExecutorWorkspace(user);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("date", appealDate);
        parameters.put("appealNumber", appeal.getNumber());
        parameters.put("conclusion", rejectCadastreDto.conclusion());
        parameters.put("executorWorkspace", workSpace[0]);
        parameters.put("executorFullWorkspace", workSpace[0] + " " + workSpace[1]);
        parameters.put("executorName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reject", parameters, true);
    }

    private String[] getSplitDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy MMMM dd HH:mm", Locale.of("uz"))).split(" ");
    }

    private String[] getExecutorWorkspace(User user) {
        Profile profile = profileService.getProfile(user.getProfileId());

        return switch (user.getRole()) {
            case Role.REGIONAL -> new String[]{officeService.findById(profile.getOfficeId()).getName(), "boshlig'i"};
            case Role.MANAGER ->
                    new String[]{departmentService.getById(profile.getDepartmentId()).getName(), "mas'ul xodimi"};
            default ->
                    throw new CustomException("Sizda arizani rad etish huquqi yo'q. Tizimda sizning rolingiz : " + user.getRole().name());
        };
    }
}
