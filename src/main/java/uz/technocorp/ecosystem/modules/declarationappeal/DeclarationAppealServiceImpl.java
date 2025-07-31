package uz.technocorp.ecosystem.modules.declarationappeal;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessService;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.dto.AppealExecutionProcessDto;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto.ConfirmPassportDto;
import uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto.RejectPassportDto;
import uz.technocorp.ecosystem.modules.declaration.DeclarationService;
import uz.technocorp.ecosystem.modules.department.DepartmentService;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
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
import java.util.*;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class DeclarationAppealServiceImpl implements DeclarationAppealService {


    private final AppealService appealService;
    private final TemplateService templateService;
    private final ProfileService profileService;
    private final OfficeService officeService;
    private final DepartmentService departmentService;
    private final AttachmentService attachmentService;
    private final AppealRepository appealRepository;
    private final DocumentService documentService;
    private final AppealExecutionProcessService appealExecutionProcessService;
    private final DeclarationService declarationService;

    @Override
    public String generateConfirmationPdf(User user, ConfirmPassportDto confirmPassportDto) {

        Appeal appeal = appealService.findById(confirmPassportDto.appealId());
        Template template = templateService.getByType(TemplateType.REPLY_ACCEPT_DECLARATION_APPEAL.name());

        String[] appealFormattedDate = getSplitDate(appeal.getCreatedAt());
        String appealDate = appealFormattedDate[0] + " yil " + appealFormattedDate[2] + " " + appealFormattedDate[1];

        String[] confirmFormattedDate = getSplitDate(LocalDateTime.now());
        String confirmationDate = confirmFormattedDate[0] + " yil " + confirmFormattedDate[2] + " " + confirmFormattedDate[1];

        String[] workSpace = getExecutorWorkspace(user);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", appeal.getOwnerName());
        parameters.put("date", appealDate);
        parameters.put("appealNumber", appeal.getNumber());
        parameters.put("executorWorkspace", workSpace[0]);
        parameters.put("confirmationDate", confirmationDate);
        parameters.put("registryNumber", confirmPassportDto.registryNumber());
        parameters.put("executorFullWorkspace", workSpace[0] + " " + workSpace[1]);
        parameters.put("executorName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/confirm", parameters, true);
    }

    @Override
    public String generateRejectionPdf(User user, RejectPassportDto rejectPassportDto) {

        Appeal appeal = appealService.findById(rejectPassportDto.appealId());
        Template template = templateService.getByType(TemplateType.REPLY_REJECT_DECLARATION_APPEAL.name());

        String[] appealFormattedDate = getSplitDate(appeal.getCreatedAt());
        String appealDate = appealFormattedDate[0] + " yil " + appealFormattedDate[2] + " " + appealFormattedDate[1];

        String[] workSpace = getExecutorWorkspace(user);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", appeal.getOwnerName());
        parameters.put("date", appealDate);
        parameters.put("appealNumber", appeal.getNumber());
        parameters.put("conclusion", rejectPassportDto.conclusion());
        parameters.put("executorWorkspace", workSpace[0]);
        parameters.put("executorFullWorkspace", workSpace[0] + " " + workSpace[1]);
        parameters.put("executorName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reject", parameters, true);
    }

    @Override
    @Transactional
    public void confirm(User user, SignedReplyDto<ConfirmPassportDto> replyDto, HttpServletRequest request) {

        Appeal appeal = findAppealByIdAndStatus(replyDto.getDto().appealId(), AppealStatus.NEW);

        // Create a reply document
//        documentService.create(new DocumentDto(appeal.getId(), DocumentType.REPLY_LETTER, replyDto.getFilePath(), replyDto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId()), AgreementStatus.APPROVED));

        // update appeal
        String conclusion = new StringBuilder()
                .append("Restrga ")
                .append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .append(" kuni ")
                .append(replyDto.getDto().registryNumber())
                .append("-son bilan ro'yxatga olindi")
                .toString();
        appeal.setExecutorId(user.getId());
        appeal.setExecutorName(user.getName());
        appeal.setConclusion(conclusion);
        appeal.setStatus(AppealStatus.COMPLETED);
        appealRepository.save(appeal);

        // Create an execution process by the appeal
        appealExecutionProcessService.create(new AppealExecutionProcessDto(appeal.getId(), AppealStatus.COMPLETED, null));

        // create declaration
        declarationService.create(appeal, replyDto.getDto().registryNumber());
    }

    @Override
    @Transactional
    public void reject(User user, SignedReplyDto<RejectPassportDto> replyDto, HttpServletRequest request) {
        Appeal appeal = findAppealByIdAndStatus(replyDto.getDto().appealId(), AppealStatus.NEW);

        // Create a reply document
        documentService.create(new DocumentDto(appeal.getId(), DocumentType.REPLY_LETTER, replyDto.getFilePath(), replyDto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId()), AgreementStatus.APPROVED));

        // update appeal
        appeal.setExecutorId(user.getId());
        appeal.setExecutorName(user.getName());
        appeal.setConclusion(replyDto.getDto().conclusion());
        appeal.setStatus(AppealStatus.REJECTED);
        appealRepository.save(appeal);

        // Create an execution process by the appeal
        appealExecutionProcessService.create(new AppealExecutionProcessDto(appeal.getId(), AppealStatus.COMPLETED, replyDto.getDto().conclusion()));
    }

    private Appeal findAppealByIdAndStatus(UUID appealId, AppealStatus appealStatus) {
        return appealRepository.findByIdAndStatus(appealId, appealStatus).orElseThrow(()->new ResourceNotFoundException("Ariza", "ID va status", appealId + ", "+ appealStatus));
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
