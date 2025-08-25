package uz.technocorp.ecosystem.modules.appeal.pdfservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationRejectionDto;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.ReplyAttestationDto;
import uz.technocorp.ecosystem.modules.appeal.dto.ReplyDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;
import uz.technocorp.ecosystem.modules.appeal.processor.AppealPdfProcessor;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.department.DepartmentService;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.AttractionPassportDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.register.dto.EquipmentAppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.unofficialregister.dto.UnofficialEquipmentAppealDto;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAppealDto;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.template.Template;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 20.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AppealPdfServiceImpl implements AppealPdfService {

    private final Map<Class<? extends AppealDto>, AppealPdfProcessor> processors;
    private final AttachmentService attachmentService;
    private final DepartmentService departmentService;
    private final HazardousFacilityService hfService;
    private final TemplateService templateService;
    private final ProfileService profileService;
    private final AppealService appealService;
    private final OfficeService officeService;
    private final UserService userService;

    @Override
    public String preparePdfWithParam(AppealDto dto, User user) {
        AppealPdfProcessor processor = findProcessor(dto);
        if (processor == null) {
            throw new CustomException("Form obyekt turi xato: " + dto.getClass().getSimpleName());
        }
        return processor.preparePdfWithParam(dto, user);
    }

    @Override
    public String prepareReplyPdfWithParam(User user, ReplyDto dto) {
        Appeal appeal = appealService.findById(dto.getAppealId());

        // Generate PDF and return path
        return switch (appeal.getAppealType().sort) {
            case "registerHf" -> makeHfReplyPdf(user, dto, appeal);
            case "deregisterHf" -> makeHfDeregisterReplyPdf(user, dto, appeal);
            case "registerEquipment", "deregisterEquipment", "reRegisterEquipment" ->
                    makeEquipmentReplyPdf(user, dto, appeal);
            case "registerIrs" -> makeIrsReplyPdf(user, dto, appeal);
            case "registerAttractionPassport" -> makeAttractionPassportReplyPdf(user, dto, appeal);
            default ->
                    throw new CustomException(appeal.getAppealType().name() + " uchun javob xati shakllantirish qilinmagan");
        };
    }

    @Override
    public String prepareRegionalAcceptPdfWithParam(User user, SetInspectorDto dto) {
        Appeal appeal = appealService.findById(dto.appealId());
        User inspector = userService.findById(dto.inspectorId());
        Template template = templateService.getByType(TemplateType.REPLY_REGIONAL_TO_APPEAL.name());

        // Appeal date
        String[] splitAppealDate = getSplitDate(appeal.getCreatedAt());
        String appealDate = splitAppealDate[0] + " yil " + splitAppealDate[2] + " " + splitAppealDate[1];

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", appeal.getOwnerName());
        parameters.put("date", appealDate);
        parameters.put("officeName", appeal.getOfficeName());
        parameters.put("inspectorName", inspector.getName());
        parameters.put("comment", dto.resolution());
        parameters.put("userName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/attestation", parameters, true);
    }

    @Override
    public String prepareCommitteeAcceptPdfWithParam(User user, ReplyAttestationDto dto) {
        Appeal appeal = appealService.findById(dto.getAppealId());
        Template template = templateService.getByType(TemplateType.REPLY_COMMITTEE_TO_APPEAL.name());

        // Attestation date
        String[] splitAttDate = getSplitDate(dto.getDateOfAttestation());
        String dateOfAttestation = splitAttDate[0] + " yil " + splitAttDate[2] + " " + splitAttDate[1];

        // Appeal date
        String[] splitAppealDate = getSplitDate(appeal.getCreatedAt());
        String appealDate = splitAppealDate[0] + " yil " + splitAppealDate[2] + " " + splitAppealDate[1];

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", appeal.getOwnerName());
        parameters.put("date", appealDate);
        parameters.put("officeName", appeal.getOfficeName());
        parameters.put("dateOfAttestation", dateOfAttestation);
        parameters.put("timeOfAttestation", splitAttDate[3]);
        parameters.put("comment", dto.getResolution());
        parameters.put("userName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/attestation", parameters, true);
    }

    @Override
    public String prepareRejectPdfWithParam(User user, ReplyDto dto) {
        Appeal appeal = appealService.findById(dto.getAppealId());

        Template template = templateService.getByType(TemplateType.REJECT_APPEAL.name());

        String[] formattedDate = getSplitDate(appeal.getCreatedAt());
        String appealDate = formattedDate[2] + " yil " + formattedDate[0] + " " + formattedDate[1];

        String[] workSpace = getExecutorWorkspace(user);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", appeal.getOwnerName());
        parameters.put("date", appealDate);
        parameters.put("appealNumber", appeal.getNumber());
        parameters.put("executorWorkspace", workSpace[0]);
        parameters.put("conclusion", dto.getConclusion());
        parameters.put("executorFullWorkspace", workSpace[0] + " " + workSpace[1]);
        parameters.put("executorName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reject", parameters, true);
    }

    @Override
    public String prepareAccreditationPdfWithParam(User user, AccreditationRejectionDto accreditationRejectionDto, boolean rejected) {
        //TODO rejected paramdan foydalanib arizani qaytarish va arizani rad etish holatlari uchun
        // ikki xil pdf yaratish kerak

        return "/files/appeals/reply/2025/june/2/1748849551879.pdf";
    }

    private AppealPdfProcessor findProcessor(AppealDto dto) {
        // 1. Exact match qidirish
        AppealPdfProcessor processor = processors.get(dto.getClass());
        if (processor != null) {
            return processor;
        }

        // 2. Parent class orqali qidirish
        if (dto instanceof EquipmentAppealDto) {
            return processors.get(EquipmentAppealDto.class);
        }

        if (dto instanceof UnofficialEquipmentAppealDto) {
            return processors.get(UnofficialEquipmentAppealDto.class);
        }

        return null;
    }

    private Map<String, String> buildBaseParameters(String userName, ReplyDto replyDto, Appeal appeal) {
        // Current date
        String[] formattedDate = getSplitDate(LocalDateTime.now());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("day", formattedDate[2]);
        parameters.put("month", formattedDate[1]);
        parameters.put("year", formattedDate[0]);
        parameters.put("officeName", appeal.getOfficeName());
        parameters.put("inspectorName", userName);
        parameters.put("legalName", appeal.getOwnerName());
        parameters.put("legalTin", appeal.getOwnerIdentity().toString());
        parameters.put("conclusion", replyDto.getConclusion());

        return parameters;
    }

    private String makeHfReplyPdf(User user, ReplyDto replyDto, Appeal appeal) {
        Template template = templateService.getByType(TemplateType.REPLY_HF_APPEAL.name());

        String[] fullAddress = appeal.getAddress().split(","); // Region , District , Address

        Map<String, String> parameters = buildBaseParameters(user.getName(), replyDto, appeal);

        parameters.put("regionName", fullAddress[0]);
        parameters.put("districtName", fullAddress[1]);
        parameters.put("address", fullAddress[2]);
        parameters.put("name", appeal.getData().path("name").asText("-"));
        parameters.put("upperOrganization", appeal.getData().path("upperOrganization").asText("Mavjud emas"));
        parameters.put("hfTypeName", appeal.getData().path("hfTypeName").asText("-"));
        parameters.put("extraArea", appeal.getData().path("extraArea").asText("Mavjud emas"));
        parameters.put("hazardousSubstance", appeal.getData().path("hazardousSubstance").asText("Mavjud emas"));

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/hf", parameters, true);
    }

    private String makeHfDeregisterReplyPdf(User user, ReplyDto replyDto, Appeal appeal) {
        Template template = templateService.getByType(TemplateType.REPLY_HF_DEREGISTER_APPEAL.name());

        Map<String, String> parameters = buildBaseParameters(user.getName(), replyDto, appeal);

        parameters.put("address", appeal.getAddress());
        parameters.put("name", appeal.getData().get("hfName").asText());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/hf/deregister", parameters, true);
    }

    private String makeIrsReplyPdf(User user, ReplyDto replyDto, Appeal appeal) {
        Template template = templateService.getByType(TemplateType.REPLY_IRS_APPEAL.name());

        IrsAppealDto dto = JsonParser.parseJsonData(appeal.getData(), IrsAppealDto.class);

        Map<String, String> parameters = buildBaseParameters(user.getName(), replyDto, appeal);

        parameters.put("identifierType", dto.getIdentifierType());
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("serialNumber", dto.getSerialNumber());
        parameters.put("type", dto.getType());
        parameters.put("category", dto.getCategory());
        parameters.put("address", appeal.getAddress());
        parameters.put("manufacturedAt", dto.getManufacturedAt());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/irs", parameters, true);
    }

    private String makeEquipmentReplyPdf(User user, ReplyDto replyDto, Appeal appeal) {
        Template template = templateService.getByType(TemplateType.REPLY_EQUIPMENT_APPEAL.name());

        Map<String, String> parameters = buildBaseParameters(user.getName(), replyDto, appeal);

        parameters.put("model", appeal.getData().get("model").asText());
        parameters.put("manufacturedAt", appeal.getData().get("manufacturedAt").asText());
        parameters.put("factoryNumber", appeal.getData().get("factoryNumber").asText());
        parameters.put("type", EquipmentType.valueOf(appeal.getData().get("type").asText()).value);
        parameters.put("subType", appeal.getData().get("childEquipmentName").asText());
        parameters.put("factory", appeal.getData().get("factory").asText());
        parameters.put("address", appeal.getAddress());

        String hazardousFacilityId = appeal.getData().get("hazardousFacilityId").asText();
        parameters.put("hazardousFacilityName",
                hazardousFacilityId != null && !hazardousFacilityId.isBlank() && !"null".equals(hazardousFacilityId)
                        ? hfService.getHfNameById(UUID.fromString(hazardousFacilityId))
                        : "Mavjud emas");

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/equipment", parameters, true);
    }

    private String makeAttractionPassportReplyPdf(User user, ReplyDto replyDto, Appeal appeal) {
        Template template = templateService.getByType(TemplateType.REPLY_ATTRACTION_PASSPORT_APPEAL.name());
        AttractionPassportDto dto = JsonParser.parseJsonData(appeal.getData(), AttractionPassportDto.class);

        Map<String, String> parameters = buildBaseParameters(user.getName(), replyDto, appeal);

        parameters.put("attractionName", dto.getAttractionName());
        parameters.put("manufacturedAt", dto.getManufacturedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        parameters.put("factoryNumber", dto.getFactoryNumber());
        parameters.put("country", dto.getCountry());
        parameters.put("type", EquipmentType.valueOf(appeal.getData().get("type").asText()).value);
        parameters.put("subType", dto.getChildEquipmentName());
        parameters.put("subSort", dto.getChildEquipmentSortName());
        parameters.put("address", appeal.getAddress());
        parameters.put("riskLevel", dto.getRiskLevel().value);
        parameters.put("acceptedAt", dto.getAcceptedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/equipment", parameters, true);
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

    private String[] getSplitDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy MMMM dd HH:mm", Locale.of("uz"))).split(" ");
    }
}
