package uz.technocorp.ecosystem.modules.appeal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.*;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcess;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessRepository;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.BoilerDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.hftype.HfType;
import uz.technocorp.ecosystem.modules.hftype.HfTypeRepository;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAppealDto;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.template.Template;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.utils.Generator;

import java.time.LocalDate;
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
    private final AppealExecutionProcessRepository appealExecutionProcessRepository;
    private final UserRepository userRepository;
    private final AppealRepository appealRepository;
    private final ProfileRepository profileRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final OfficeRepository officeRepository;
    private final TemplateService templateService;
    private final DocumentService documentService;
    private final Generator generator;
    private final AttachmentService attachmentService;
    private final HfTypeRepository hfTypeRepository;

    @Override
    @Transactional
    public void setInspector(SetInspectorDto dto) {
        User user = userRepository
                .findById(dto.inspectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Inspektor", "Id", dto.inspectorId()));
        Appeal appeal = repository
                .findById(dto.appealId())
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", dto.appealId()));
        appeal.setExecutorId(dto.inspectorId());
        appeal.setExecutorName(user.getName());
        appeal.setDeadline(LocalDate.parse(dto.deadline()));
        repository.save(appeal);
        repository.flush();
        appealExecutionProcessRepository.save(
                new AppealExecutionProcess(
                        dto.appealId(),
                        "Ariza inspektorga biriktirildi!"
                )
        );
    }

    @Override
    @Transactional
    public void changeAppealStatus(AppealStatusDto dto) {
        Appeal appeal = repository
                .findById(dto.appealId())
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", dto.appealId()));

        appeal.setStatus(dto.status());
        repository.save(appeal);
        appealExecutionProcessRepository.save(
                new AppealExecutionProcess(
                        dto.appealId(),
                        dto.description()
                )
        );
    }

    @Override
    public Page<AppealCustom> getAppealCustoms(User user, Map<String, String> params) {
        return appealRepository.getAppealCustoms(user, params);
    }

    @Override
    public UUID create(AppealDto dto, User user) {

        //make data
        Profile profile = profileRepository.findById(user.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profil", "ID", user.getProfileId()));
        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", dto.getRegionId()));
        District district = districtRepository.findById(dto.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("Tuman", "ID", dto.getDistrictId()));
        Office office = officeRepository.getOfficeByRegionId(region.getId()).orElseThrow(() -> new ResourceNotFoundException("Arizada ko'rsatilgan " + region.getName() + " uchun qo'mita tomonidan hududiy bo'lim qo'shilmagan"));
        String executorName = getExecutorName(dto.getAppealType());
        OrderNumberDto numberDto = makeNumber(dto.getAppealType());
        JsonNode data = makeJsonData(dto);

        Appeal appeal = Appeal
                .builder()
                .appealType(dto.getAppealType())
                .number(numberDto.number())
                .orderNumber(numberDto.orderNumber())
                .legalTin(profile.getTin())
                .legalName(profile.getLegalName())
                .legalRegionId(profile.getRegionId())
                .legalRegionName(profile.getRegionName())
                .regionId(dto.getRegionId())
                .regionName(region.getName())
                .legalDistrictId(profile.getDistrictId())
                .legalDistrictName(profile.getDistrictName())
                .districtId(dto.getDistrictId())
                .districtName(district.getName())
                .officeId(office.getId())
                .officeName(office.getName())
                .status(AppealStatus.NEW)
                .address(dto.getAddress())
                .legalAddress(profile.getLegalAddress())
                .phoneNumber(dto.getPhoneNumber())
                .deadline(dto.getDeadline())
                .executorName(executorName)
                .data(data)
                .build();
        return repository.save(appeal).getId();
    }

    @Override
    public void update(UUID id, AppealDto dto) {
        Appeal appeal = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
        appeal.setData(makeJsonData(dto));
        repository.save(appeal);
    }

    @Override
    public String generatePdfWithParam(HfAppealDto dto, User user) {

        //check the data(mainly IDs) of the dto
        HfType hfType = hfTypeRepository.findById(dto.getHfTypeId()).orElseThrow(() -> new ResourceNotFoundException("HF_Type", "ID", dto.getHfTypeId()));
        dto.setHfTypeName(hfType.getName());

        Template template = templateService.getByType(TemplateType.XICHO_APPEAL.name());
        if (template == null) {
            throw new ResourceNotFoundException("Xicho arizasi", "Template", TemplateType.XICHO_APPEAL.name());
        }
        Profile profile = profileRepository.findById(user.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profil", "ID", user.getProfileId()));

        // Collect params to Map
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", profile.getFullName());
        parameters.put("legalName", profile.getLegalName());
        parameters.put("tin", profile.getTin().toString());
        parameters.put("regionName", regionRepository.findById(dto.getRegionId()).map(Region::getName).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", dto.getRegionId())));
        parameters.put("districtName", districtRepository.findById(dto.getDistrictId()).map(District::getName).orElseThrow(() -> new ResourceNotFoundException("Tuman", "ID", dto.getDistrictId())));
        parameters.put("hfName", dto.getName());

        /**
         * attachmentga va folder ga save qilish kerak
         * file path ni qaytarib yuborish kerak
         */
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/hf-appeals", parameters);
    }

    @Override
    public String prepareReplyPdfWithParam(User user, ReplyDto replyDto) {
        /*// TODO change template type
        Template template = getTemplate(TemplateType.XICHO_APPEAL);
        Appeal appeal = repository.findById(replyDto.getAppealId()).orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", replyDto.getAppealId()));

        // Collect params to Map
        Map<String, String> parameters = new HashMap<>();
        parameters.put("officeName", "");
        parameters.put("inspectorName", "");
        parameters.put("legalName", "");
        parameters.put("legalTin", "");
        parameters.put("legalAddress", "");

        // Replace variables
        String content = replaceVariables(template.getContent(), parameters);

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(content, "appeals/reply");*/
        return null;
    }

    @Override
    @Transactional
    public void saveAndSign(User user, SignedAppealDto dto, HttpServletRequest request) {
        UUID appealId;
        switch (dto.getDto()) {
            case HfAppealDto hfAppealDto -> appealId = create(hfAppealDto, user);
            case IrsAppealDto irsAppealDto -> appealId = create(irsAppealDto, user);
            case BoilerDto boilerDto -> appealId = create(boilerDto, user);
            // TODO barcha qurilmalar uchun case yozish kerak
            default -> throw new RuntimeException("Mavjud bo'lmagan obyekt turi keldi");
        }

        // Create a document
        documentService.create(new DocumentDto(dto.getType(), appealId, dto.getFilePath(), dto.getSign(), Helper.getIp(request), user.getId()));
    }

    @Override
    public List<AppealViewByPeriod> getAllByPeriodAndInspector(User inspector, LocalDate startDate, LocalDate endDate) {
        return repository.getAllByPeriodAndInspectorId(startDate, endDate, inspector.getId(), AppealStatus.IN_PROCESS.name());
    }

    @Override
    public AppealViewById getById(UUID appealId) {
        return appealRepository.getAppealById(appealId).orElseThrow(() -> new ResourceNotFoundException("Ariza", "ID", appealId));
    }

    private JsonNode makeJsonData(AppealDto dto) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.valueToTree(dto);
    }

    private OrderNumberDto makeNumber(AppealType appealType) {
        Long orderNumber = appealRepository.getMax().orElse(0L) + 1;

        String number = null;

        switch (appealType) {
            case REGISTER_IRS, ACCEPT_IRS, TRANSFER_IRS -> number = orderNumber + "-INM-" + LocalDate.now().getYear();
            case REGISTER_HF, DEREGISTER_HF -> number = orderNumber + "-XIC-" + LocalDate.now().getYear();
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

    private Region getRegion(Integer regionId) {
        return regionRepository.findById(regionId).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", regionId));
    }

    private District getDistrict(Integer districtId) {
        return districtRepository.findById(districtId).orElseThrow(() -> new ResourceNotFoundException("Tuman", "ID", districtId));
    }

    private Profile getProfile(UUID profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> new ResourceNotFoundException("Profil", "ID", profileId));
    }

    private Template getTemplate(TemplateType type) {
        Template template = templateService.getByType(type.name());
        if (template == null) {
            throw new ResourceNotFoundException("Shablon", type.name(), "");
        }
        return template;
    }
}
