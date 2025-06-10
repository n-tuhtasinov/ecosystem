package uz.technocorp.ecosystem.modules.appeal;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.*;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.appeal.processor.AppealPdfProcessor;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessService;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.dto.AppealExecutionProcessDto;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.EquipmentAppealDto;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.hftype.HfTypeService;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSourceService;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAppealDto;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.template.Template;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.utils.JsonMaker;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository repository;
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final RegionService regionService;
    private final DistrictService districtService;
    private final OfficeRepository officeRepository;
    private final DocumentService documentService;
    private final TemplateService templateService;
    private final AttachmentService attachmentService;
    private final AppealExecutionProcessService appealExecutionProcessService;
    private final HazardousFacilityService hfService;
    private final Map<Class<? extends AppealDto>, AppealPdfProcessor> processors;
    private final IonizingRadiationSourceService ionizingRadiationSourceService;
    private final EquipmentService equipmentService;
    private final HfTypeService hfTypeService;
    private final ChildEquipmentService childEquipmentService;

    @Override
    @Transactional
    public void saveAndSign(User user, SignedAppealDto<? extends AppealDto> dto, HttpServletRequest request) {
        // Create and save appeal
        UUID appealId = create(dto.getDto(), user);

        // Create a document
        documentService.create(new DocumentDto(appealId, dto.getType(), dto.getFilePath(), dto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId())));
    }

    @Override
    @Transactional
    public void saveReplyAndSign(User user, SignedReplyDto replyDto, HttpServletRequest request) {
        // Check appeal by (appealId, status, inspectorId)
        Appeal appeal = repository.findByIdAndStatusAndExecutorId(replyDto.getDto().getAppealId(), AppealStatus.IN_PROCESS, user.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Bu ariza sizga biriktirilmagan yoki ariza holati o'zgargan")
                );

        //change appeal's isRejected, if it is true
        if (appeal.getIsRejected()) {
            appeal.setIsRejected(false);
            repository.save(appeal);
        }

        // Create a reply document
        documentService.create(new DocumentDto(appeal.getId(), replyDto.getType(), replyDto.getFilePath(), replyDto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId())));

        // Change appealStatus and set conclusion
        repository.changeStatusAndSetConclusion(appeal.getId(), replyDto.getDto().getConclusion(), AppealStatus.IN_AGREEMENT);

        // Create an execution process by the appeal
        appealExecutionProcessService.create(new AppealExecutionProcessDto(appeal.getId(), AppealStatus.IN_AGREEMENT, null));
    }

    @Override
    public UUID create(AppealDto dto, User user) {

        //make data
        Profile profile = profileService.getProfile(user.getProfileId());
        Region region = regionService.getById(dto.getRegionId());
        District district = districtService.getDistrict(dto.getDistrictId());
        Office office = officeRepository.getOfficeByRegionId(region.getId()).orElseThrow(() -> new ResourceNotFoundException("Arizada ko'rsatilgan " + region.getName() + " uchun qo'mita tomonidan hududiy bo'lim qo'shilmagan"));
        String executorName = getExecutorName(dto.getAppealType());
        OrderNumberDto numberDto = makeNumber(dto.getAppealType());
        JsonNode data = JsonMaker.makeJsonSkipFields(dto);

        Appeal appeal = Appeal
                .builder()
                .appealType(dto.getAppealType())
                .number(numberDto.number())
                .orderNumber(numberDto.orderNumber())
                .legalTin(profile.getTin())
                .legalName(profile.getLegalName())
                .legalRegionId(profile.getRegionId())
                .profileId(profile.getId())
                .regionId(dto.getRegionId())
                .legalDistrictId(profile.getDistrictId())
                .districtId(dto.getDistrictId())
                .officeId(office.getId())
                .officeName(office.getName())
                .status(AppealStatus.NEW)
                .address(region.getName() + ", " + district.getName() + ", " + dto.getAddress())
                .legalAddress(profile.getLegalAddress())
                .phoneNumber(dto.getPhoneNumber())
                .deadline(dto.getDeadline())
                .executorName(executorName)
                .data(data)
                .isRejected(false)
                .build();
        repository.save(appeal);

        //create appeal execution process
        appealExecutionProcessService.create(new AppealExecutionProcessDto(appeal.getId(), AppealStatus.NEW, null));

        return appeal.getId();
    }

    @Override
    public void update(UUID id, AppealDto dto) {
        Appeal appeal = getAppealById(id);
        appeal.setData(JsonMaker.makeJsonSkipFields(dto));
        repository.save(appeal);
    }

    @Override
    @Transactional
    public void setInspector(SetInspectorDto dto) {
        User user = userRepository
                .findById(dto.inspectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Inspektor", "Id", dto.inspectorId()));
        Appeal appeal = getAppealById(dto.appealId());

        appeal.setExecutorId(dto.inspectorId());
        appeal.setExecutorName(user.getName());
        appeal.setDeadline(dto.deadline());
        appeal.setResolution(dto.resolution());
        appeal.setStatus(AppealStatus.IN_PROCESS);
        repository.save(appeal);

        // create an execution process by appeal
        appealExecutionProcessService.create(new AppealExecutionProcessDto(dto.appealId(), AppealStatus.IN_PROCESS, null));
    }

    @Override
    public Page<AppealCustom> getAppealCustoms(User user, Map<String, String> params) {
        return repository.getAppealCustoms(user, params);
    }

    @Override
    public List<AppealViewByPeriod> getAllByPeriodAndInspector(User inspector, LocalDate startDate, LocalDate endDate) {
        return repository.getAllByPeriodAndInspectorId(startDate, endDate, inspector.getId(), AppealStatus.IN_PROCESS.name());
    }

    @Override
    public AppealViewById getById(UUID appealId) {
        return repository.getAppealById(appealId).orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", appealId));
    }

    @Override
    public String preparePdfWithParam(AppealDto dto, User user) {
        AppealPdfProcessor processor = findProcessor(dto);
        if (processor == null) {
            throw new ResourceNotFoundException("Form obyekt turi xato: " + dto.getClass().getSimpleName());
        }
        return processor.preparePdfWithParam(dto, user);
    }

    @Override
    public String prepareReplyPdfWithParam(User user, ReplyDto replyDto) {
        Appeal appeal = getAppealById(replyDto.getAppealId());

        String path;
        switch (appeal.getAppealType().sort) {
            case "registerHf" -> path = makeHfReplyPdf(user, replyDto, appeal);
            case "registerEquipment" -> path = makeEquipmentReplyPdf(user, replyDto, appeal);
            case "registerIrs" -> path = makeIrsReplyPdf(user, replyDto, appeal);
            default ->
                    throw new CustomException(appeal.getAppealType().name() + " uchun javob xati shakllantirish qilinmagan");
        }
        return path;
    }

    @Override
    @Transactional
    public void reject(User user, RejectDto dto) {
        Appeal appeal = repository.findById(dto.appealId()).orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", dto.appealId()));
        appeal.setStatus(AppealStatus.IN_PROCESS);
        appeal.setIsRejected(true);
        repository.save(appeal);

        //set rejection to the document
        documentService.reject(user, dto);

        // create an execution process by appeal
        appealExecutionProcessService.create(new AppealExecutionProcessDto(dto.appealId(), AppealStatus.IN_PROCESS, dto.description()));
    }

    @Override
    @Transactional
    public void confirm(User user, ConfirmationDto dto) {
        Appeal appeal = repository.findById(dto.appealId()).orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", dto.appealId()));

        Role role = user.getRole();
        AppealStatus appealStatus;
        if (role == Role.REGIONAL) {
            if (!appeal.getStatus().equals(AppealStatus.IN_PROCESS)) {
                throw new RuntimeException("Ariza holati 'IN_PROCESS' emas. Hozirgi holati: "+appeal.getStatus().name());
            }
            appealStatus = AppealStatus.IN_APPROVAL;
        } else if (role == Role.MANAGER) {
            if (!appeal.getStatus().equals(AppealStatus.IN_APPROVAL)) {
                throw new RuntimeException("Ariza holati 'IN_APPROVAL' emas. Hozirgi holati: "+appeal.getStatus().name());
            }
            appealStatus = AppealStatus.COMPLETED;
        } else {
            throw new RuntimeException(role.name() + " roli uchun hali logika yozilmagan. Backendchilarga ayting ))) ...");
        }

        appeal.setStatus(appealStatus);
        appeal.setIsRejected(false); //because it may be confirming the previously rejected appeal.
        repository.save(appeal);

        //set confirmation to the document
        documentService.confirmationByAppeal(user, dto.documentId());

        // create an execution process by appeal
        appealExecutionProcessService.create(new AppealExecutionProcessDto(dto.appealId(), appealStatus, null));

        //create registry for the appeal if the appeal's status is completed
        if (appealStatus == AppealStatus.COMPLETED) {
            switch (appeal.getAppealType().sort) {
                case "registerHf" -> hfService.create(appeal);
                case "registerIrs" -> ionizingRadiationSourceService.create(appeal);
                case "registerEquipment" -> equipmentService.create(appeal);
                //TODO: boshqa turdagi arizalar uchun ham registr ochilishini yozish kerak
            }
        }
    }

    @Override
    public void setHfNameAndChildEquipmentName(EquipmentAppealDto dto) {
        if (dto.getHazardousFacilityId() != null) {
            String hfName = hfService.getHfNameById(dto.getHazardousFacilityId());
            dto.setHazardousFacilityName(hfName);
        }
        String name = childEquipmentService.getChildEquipmentNameById(dto.getChildEquipmentId());
        dto.setChildEquipmentName(name);
    }

    @Override
    public void setHfTypeName(HfAppealDto appealDto) {
        String hfTypeName = hfTypeService.getHfTypeNameById(appealDto.getHfTypeId());
        appealDto.setHfTypeName(hfTypeName);
    }

    private OrderNumberDto makeNumber(AppealType appealType) {
        Long orderNumber = repository.getMax().orElse(0L) + 1;

        String number = null;

        switch (appealType.sort) {
            case "registerIrs" -> number = orderNumber + "-INM-" + LocalDate.now().getYear();
            case "registerHf" -> number = orderNumber + "-XIC-" + LocalDate.now().getYear();
            case "registerEquipment", "reRegisterEquipment" ->
                    number = orderNumber + "-QUR-" + LocalDate.now().getYear();
            // TODO: Ariza turiga qarab ariza raqamini shakllantirishni davom ettirish kerak
        }
        return new OrderNumberDto(orderNumber, number);
    }

    private String getExecutorName(AppealType appealType) {
        String executorName = null;

        switch (appealType) {
            case REGISTER_IRS, ACCEPT_IRS, TRANSFER_IRS -> executorName = "INM ijrochi ismi";
            case ACCREDIT_EXPERT_ORGANIZATION -> executorName = "kimdir";
            case REGISTER_DECLARATION -> executorName = "yana kimdir";
            //TODO: Ariza turiga qarab ariza ijrochi shaxs kimligini shakllantirishni davom ettirish kerak
        }
        return executorName;
    }

    private Appeal getAppealById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", id));
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
        return null;
    }

    private Map<String, String> buildBaseParameters(String userName, ReplyDto replyDto, Appeal appeal) {
        // Current date
        String[] formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.of("uz"))).split(" ");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("day", formattedDate[0]);
        parameters.put("month", formattedDate[1]);
        parameters.put("year", formattedDate[2]);
        parameters.put("officeName", appeal.getOfficeName());
        parameters.put("inspectorName", userName);
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("legalTin", appeal.getLegalTin().toString());
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
        parameters.put("name", appeal.getData().get("name").asText());
        parameters.put("upperOrganization", appeal.getData().get("upperOrganization").asText());
        parameters.put("appealType", appeal.getAppealType().label);
        parameters.put("extraArea", appeal.getData().get("extraArea").asText());
        parameters.put("hazardousSubstance", appeal.getData().get("hazardousSubstance").asText());


        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/hf", parameters, true);
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

}
