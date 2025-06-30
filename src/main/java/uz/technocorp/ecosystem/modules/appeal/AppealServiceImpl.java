package uz.technocorp.ecosystem.modules.appeal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessService;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.dto.AppealExecutionProcessDto;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.attestation.AttestationService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.hftype.HfTypeService;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSourceService;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.utils.JsonMaker;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final IonizingRadiationSourceService ionizingRadiationSourceService;
    private final AppealExecutionProcessService appealExecutionProcessService;
    private final AttestationService attestationService;
    private final AttachmentService attachmentService;
    private final HazardousFacilityService hfService;
    private final EquipmentService equipmentService;
    private final DocumentService documentService;
    private final DistrictService districtService;
    private final ProfileService profileService;
    private final RegionService regionService;
    private final HfTypeService hfTypeService;
    private final OfficeService officeService;
    private final AppealRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public UUID saveAndSign(User user, SignedAppealDto<? extends AppealDto> dto, HttpServletRequest request) {
        // Create and save appeal
        UUID appealId = create(dto.getDto(), user);

        // Create a document
        createDocument(new DocumentDto(appealId, dto.getType(), dto.getFilePath(), dto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId()), null));

        // Delete files from Attachment
        attachmentService.deleteByPaths(dto.getDto().getFiles().values());

        return appealId;
    }

    @Override
    @Transactional
    public void saveReplyAndSign(User user, SignedReplyDto<ReplyDto> replyDto, HttpServletRequest request) {
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
        createDocument(new DocumentDto(appeal.getId(), DocumentType.REPORT, replyDto.getFilePath(), replyDto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId()), null));

        // Change appealStatus and set conclusion
        repository.changeStatusAndSetConclusion(appeal.getId(), replyDto.getDto().getConclusion(), AppealStatus.IN_AGREEMENT);

        // Create an execution process by the appeal
        createExecutionProcess(new AppealExecutionProcessDto(appeal.getId(), AppealStatus.IN_AGREEMENT, replyDto.getDto().getConclusion()));
    }

    @Override
    @Transactional
    public void replyAccept(User user, SignedReplyDto<?> replyDto, HttpServletRequest request) {
        switch (user.getRole()) {
            case REGIONAL -> acceptByRegional(user, replyDto, request);
            case MANAGER, HEAD -> acceptByCommittee(user, replyDto, request);
            default -> throw new CustomException("Sizda attestatsiyaga javob berish huquqi yo'q");
        }
    }

    @Override
    @Transactional
    public void replyReject(User user, SignedReplyDto<ReplyDto> replyDto, HttpServletRequest request) {
        Integer officeId = getProfile(user.getProfileId()).getOfficeId();

        Appeal appeal = switch (user.getRole()) {
            case Role.MANAGER -> findByIdAndStatusAndSetExecutorName(replyDto.getDto().getAppealId(), AppealStatus.NEW, user);
            case Role.REGIONAL -> findByIdStatusAndOffice(replyDto.getDto().getAppealId(), AppealStatus.NEW, officeId);
            default -> throw new CustomException("Sizda arizalarni rad qilish imkoniyati mavjud emas");
        };

        // Create a reply document
        createDocument(new DocumentDto(appeal.getId(), DocumentType.REPLY_LETTER, replyDto.getFilePath(), replyDto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId()), AgreementStatus.APPROVED));

        // Change appealStatus and set conclusion
        repository.changeStatusAndSetConclusion(appeal.getId(), replyDto.getDto().getConclusion(), AppealStatus.CANCELED);

        // Create an execution process by the appeal
        createExecutionProcess(new AppealExecutionProcessDto(appeal.getId(), AppealStatus.CANCELED, replyDto.getDto().getConclusion()));
    }

    @Override
    public UUID create(AppealDto dto, User user) {

        //make data
        Profile profile = getProfile(user.getProfileId());
        Region region = regionService.findById(dto.getRegionId());
        District district = districtService.findById(dto.getDistrictId());
        Office office = officeService.findByRegionId(region.getId());
        String executorName = getExecutorName(dto.getAppealType());
        OrderNumberDto numberDto = makeNumber(dto.getAppealType());
        JsonNode data = JsonMaker.makeJsonSkipFields(dto);

        // Ariza statusini ariza turiga qarab belgilash;
        AppealStatus appealStatus = getAppealStatus(dto.getAppealType());

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
                .status(appealStatus)
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
        createExecutionProcess(new AppealExecutionProcessDto(appeal.getId(), appealStatus, null));

        return appeal.getId();
    }

    @Override
    public void update(UUID id, AppealDto dto) {
        Appeal appeal = findById(id);
        appeal.setData(JsonMaker.makeJsonSkipFields(dto));
        repository.save(appeal);
    }

    @Override
    @Transactional
    public void setInspector(SetInspectorDto dto) {
        User user = userService.findById(dto.inspectorId());
        Appeal appeal = findById(dto.appealId());

        appeal.setExecutorId(dto.inspectorId());
        appeal.setExecutorName(user.getName());
        appeal.setDeadline(dto.deadline());
        appeal.setResolution(dto.resolution());
        appeal.setStatus(AppealStatus.IN_PROCESS);
        repository.save(appeal);

        // create an execution process by appeal
        createExecutionProcess(new AppealExecutionProcessDto(dto.appealId(), AppealStatus.IN_PROCESS, null));
    }

    @Override
    public Page<AppealCustom> getAppealCustoms(User user, Map<String, String> params) {
        //get profile by user
        Profile profile = profileService.getProfile(user.getProfileId());
        List<AppealType> appealTypes = null;

        //to display data by user role
        switch (user.getRole()) {
            case LEGAL -> params.put("legalTin", profile.getTin().toString());
            case INSPECTOR -> params.put("executorId", user.getId().toString());
            case REGIONAL -> putRegionIdSafely(params, profile);
            case MANAGER, HEAD, CHAIRMAN -> appealTypes = getAppealTypes(user);
            //TODO: Qolgan roli uchun ko'rinishni qilish kerak
            default -> throw new RuntimeException("Ushbu role uchun logika ishlab chiqilmagan!");
        }
        return repository.getAppealCustoms(user, params, appealTypes);
    }

    private static List<AppealType> getAppealTypes(User user) {
        return user.getDirections().stream()
                .map(AppealType::getEnumListByDirection)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static void putRegionIdSafely(Map<String, String> params, Profile profile) {
        if (profile.getRegionId() == null)
            throw new RuntimeException(String.format("IDsi %s bo'lgan profile uchun regionId biriktirilmagan", profile.getId()));
        params.put("regionId", profile.getRegionId().toString());
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
    @Transactional
    public void reject(User user, RejectDto dto) {
        Appeal appeal = repository.findById(dto.appealId()).orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", dto.appealId()));
        appeal.setStatus(AppealStatus.IN_PROCESS);
        appeal.setIsRejected(true);
        repository.save(appeal);

        //set rejection to the document
        documentService.reject(user, dto);

        // create an execution process by appeal
        createExecutionProcess(new AppealExecutionProcessDto(dto.appealId(), AppealStatus.IN_PROCESS, dto.description()));
    }

    @Override
    @Transactional
    public void confirm(User user, ConfirmationDto dto) {
        Appeal appeal = repository.findById(dto.appealId()).orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", dto.appealId()));

        // get status by role
        AppealStatus appealStatus = setApproverNameAndGetAppealStatusByRole(user, appeal, dto.shouldRegister());

        //update the appeal
        appeal.setStatus(appealStatus);
        appeal.setIsRejected(false); //because it may be confirming the previously rejected appeal.
        repository.save(appeal);

        //set confirmation to the document
        documentService.confirmationByAppeal(user, dto.documentId());

        // create an execution process by appeal
        createExecutionProcess(new AppealExecutionProcessDto(dto.appealId(), appealStatus, null));

        //create registry for the appeal if the appeal's status is completed
        if (appealStatus == AppealStatus.COMPLETED) {
            switch (appeal.getAppealType().sort) {
                case "registerHf" -> hfService.create(appeal);
                case "registerIrs" -> ionizingRadiationSourceService.create(appeal);
                case "registerEquipment", "registerAttractionPassport" -> equipmentService.create(appeal);
                // akkreditatsiyani yaratish -> accreditationService.create(appeal);
                //TODO: boshqa turdagi arizalar uchun ham registr ochilishini yozish kerak
            }
        }
    }

    @Override
    public void setHfTypeName(HfAppealDto appealDto) {
        String hfTypeName = hfTypeService.getHfTypeNameById(appealDto.getHfTypeId());
        appealDto.setHfTypeName(hfTypeName);
    }

    private Appeal findByIdAndStatusAndSetExecutorName(UUID appealId, AppealStatus appealStatus, User user) {
        Appeal appeal = repository.findByIdAndStatus(appealId, appealStatus).orElseThrow(
                () -> new ResourceNotFoundException("Ariza topilmadi yoki ariza holati o'zgargan"));
        appeal.setExecutorId(user.getId());
        appeal.setExecutorName(user.getName());
        return appeal;
    }

    @Override
    public Appeal findByIdStatusAndOffice(UUID appealId, AppealStatus appealStatus, Integer officeId) {
        return repository.findByIdAndStatusAndOfficeId(appealId, appealStatus, officeId).orElseThrow(
                () -> new ResourceNotFoundException("Ariza topilmadi yoki ariza holati o'zgargan"));
    }

    @Override
    @Transactional
    public void setFilePath(User user, UploadFileDto dto) {
        Appeal appeal = findById(dto.appealId());

        ObjectNode data = (ObjectNode) appeal.getData();
        ObjectNode filesNode = (ObjectNode) data.get("files");

        if (!filesNode.has(dto.fieldName())) {
            throw new ResourceNotFoundException("Field", "nom", dto.fieldName());
        }
        String filePathToDelete = filesNode.get(dto.fieldName()).textValue();

        filesNode.put(dto.fieldName(), dto.filePath());

        appeal.setData(data);
        repository.save(appeal);

        //delete from the attachment table in order to save the file permanently
        attachmentService.deleteByPath(dto.filePath());

        //delete file from the storage if the path is not null
        if (filePathToDelete != null) {
            attachmentService.deleteFileFromStorage(filePathToDelete);
        }
    }

    @Override
    public Appeal findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", id));
    }

    @Override
    public Long getCount(User user, AppealStatus status) {
        Profile profile = getProfile(user.getProfileId());
        return switch (user.getRole()) {
            case HEAD, MANAGER, CHAIRMAN -> repository.countByParams(makeAppealCountParamsByDirections(user, status));
            case LEGAL -> repository.countByParams(new AppealCountParams(status, profile.getTin(), null, null, null));
            case INSPECTOR -> repository.countByParams(new AppealCountParams(status, null, user.getId(), null, null));
            case REGIONAL ->
                    repository.countByParams(new AppealCountParams(status, null, null, profile.getOfficeId(), null));
            //TODO: boshqa rollar uchun logika yozish kerak
            default ->
                    throw new RuntimeException(user.getRole().name() + " roli uchun hali logika yozilmagan. Backendchilarga ayting");
        };
    }

    @Override
    public List<AppealViewById> getByIds(List<UUID> appealIds) {
        if (appealIds != null && !appealIds.isEmpty()) {
            return repository.findAllByIdIn(appealIds);
        }
        return null;
    }

    private AppealCountParams makeAppealCountParamsByDirections(User user, AppealStatus status) {
        List<AppealType> appealTypes = getAppealTypes(user);
        return new AppealCountParams(status, null, null, null, appealTypes);
    }

    private AppealStatus setApproverNameAndGetAppealStatusByRole(User user, Appeal appeal, Boolean shouldRegister) {
        Role role = user.getRole();
        AppealStatus appealStatus;

        if (role == Role.REGIONAL) {
            if (!appeal.getStatus().equals(AppealStatus.IN_AGREEMENT))
                throw new RuntimeException("Ariza holati 'IN_AGREEMENT' emas. Hozirgi holati: " + appeal.getStatus().name());
            appeal.setApproverName(user.getName());
            appealStatus = AppealStatus.IN_APPROVAL;

        } else if (role == Role.MANAGER) {
            if (!appeal.getStatus().equals(AppealStatus.IN_APPROVAL))
                throw new RuntimeException("Ariza holati 'IN_APPROVAL' emas. Hozirgi holati: " + appeal.getStatus().name());
            if (shouldRegister == null) throw new RuntimeException("Reestrga qo'shish yoki qo'shmaslik belgilanmadi");
            appealStatus = shouldRegister ? AppealStatus.COMPLETED : AppealStatus.REJECTED;

        } else {
            throw new RuntimeException(role.name() + " roli uchun hali logika yozilmagan. Backendchilarga ayting ))) ...");
        }
        return appealStatus;
    }

    private OrderNumberDto makeNumber(AppealType appealType) {
        Long orderNumber = repository.getMax().orElse(0L) + 1;

        String number = null;

        switch (appealType.sort) {
            case "registerIrs" -> number = orderNumber + "-INM-" + LocalDate.now().getYear();
            case "registerHf" -> number = orderNumber + "-XIC-" + LocalDate.now().getYear();
            case "registerEquipment", "reRegisterEquipment" ->
                    number = orderNumber + "-QUR-" + LocalDate.now().getYear();
            case "registerAttractionPassport", "reRegisterAttractionPassport" ->
                    number = orderNumber + "-ATP-" + LocalDate.now().getYear();
            case "accreditExpertOrganization", "reAccreditExpertOrganization", "expendAccreditExpertOrganization" ->
                    number = orderNumber + "-AKK-" + LocalDate.now().getYear();
            case "registerExpertiseConclusion" -> number = orderNumber + "-EXP-" + LocalDate.now().getYear();
            case "registerAttestation" -> number = orderNumber + "-ATT-" + LocalDate.now().getYear();
            case "registerCadastrePassport" -> number = orderNumber + "-CAD-" + LocalDate.now().getYear();
            case "registerDeclaration" -> number = orderNumber + "-DEC-" + LocalDate.now().getYear();
            // TODO: Ariza turiga qarab ariza raqamini shakllantirishni davom ettirish kerak

            default -> throw new RuntimeException("Ushbu ariza turi uchun ariza registratsiya raqami shakllantirish hali qilinmagan");

        }
        return new OrderNumberDto(orderNumber, number);
    }

    private String getExecutorName(AppealType appealType) {
        String executorName = null;

        switch (appealType) {
            case REGISTER_IRS, ACCEPT_IRS, TRANSFER_IRS -> executorName = "INM ijrochi ismi";
            case ACCREDIT_EXPERT_ORGANIZATION, RE_ACCREDIT_EXPERT_ORGANIZATION, EXPEND_ACCREDITATION_SCOPE ->
                    executorName = "kimdir";
            case REGISTER_DECLARATION, REGISTER_CADASTRE_PASSPORT -> executorName = "Axborot-tahlil, akkreditatsiyalash, kadastrni yuritish va ijro nazorati boshqarmasi bosh mutaxassisi";
            //TODO: Ariza turiga qarab ariza ijrochi shaxs kimligini shakllantirishni davom ettirish kerak
        }
        return executorName;
    }

    private AppealStatus getAppealStatus(AppealType appealType) {
        switch (appealType) {
            case AppealType.ACCREDIT_EXPERT_ORGANIZATION,
                 AppealType.RE_ACCREDIT_EXPERT_ORGANIZATION,
                 AppealType.EXPEND_ACCREDITATION_SCOPE,
                 AppealType.REGISTER_EXPERTISE_CONCLUSION -> {
                return AppealStatus.IN_APPROVAL;
            }
            default -> {
                return AppealStatus.NEW;
            }
        }
    }

    private void acceptByCommittee(User user, SignedReplyDto<?> replyDto, HttpServletRequest request) {
        ReplyAttestationDto dto = (ReplyAttestationDto) replyDto.getDto();
        Appeal appeal = repository.findByIdAndStatusAndAppealType(dto.getAppealId(), AppealStatus.NEW, AppealType.ATTESTATION_COMMITTEE)
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", dto.getAppealId()));
        appeal.setExecutorId(user.getId());
        appeal.setExecutorName(user.getName());
        appeal.setDeadline(dto.getDateOfAttestation().toLocalDate());
        appeal.setResolution(dto.getResolution());
        appeal.setStatus(AppealStatus.COMPLETED);
        repository.save(appeal);

        // Create a reply document
        createDocument(new DocumentDto(appeal.getId(), DocumentType.REPLY_LETTER, replyDto.getFilePath(), replyDto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId()), AgreementStatus.APPROVED));

        // Create an execution process by the appeal
        createExecutionProcess(new AppealExecutionProcessDto(appeal.getId(), AppealStatus.COMPLETED, dto.getResolution()));

        // Create attestation
        attestationService.create(appeal);
    }

    private void acceptByRegional(User user, SignedReplyDto<?> replyDto, HttpServletRequest request) {
        SetInspectorDto dto = (SetInspectorDto) replyDto.getDto();
        User inspector = userService.findById(dto.inspectorId());
        Integer officeId = getProfile(user.getProfileId()).getOfficeId();

        Appeal appeal = repository.findByIdAndStatusAndOfficeIdAndAppealType(dto.appealId(), AppealStatus.NEW, officeId, AppealType.ATTESTATION_REGIONAL)
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", dto.appealId()));
        appeal.setExecutorId(inspector.getId());
        appeal.setExecutorName(inspector.getName());
        appeal.setDeadline(dto.deadline());
        appeal.setResolution(dto.resolution());
        appeal.setStatus(AppealStatus.COMPLETED);
        repository.save(appeal);

        // Create a reply document
        createDocument(new DocumentDto(appeal.getId(), DocumentType.REPLY_LETTER, replyDto.getFilePath(), replyDto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId()), AgreementStatus.APPROVED));

        // Create an execution process by the appeal
        createExecutionProcess(new AppealExecutionProcessDto(appeal.getId(), AppealStatus.COMPLETED, dto.resolution()));

        // Create attestation
        attestationService.create(appeal);
    }

    private Profile getProfile(UUID profileId) {
        return profileService.getProfile(profileId);
    }

    private void createDocument(DocumentDto dto) {
        documentService.create(dto);
    }

    private void createExecutionProcess(AppealExecutionProcessDto dto) {
        appealExecutionProcessService.create(dto);
    }
}
