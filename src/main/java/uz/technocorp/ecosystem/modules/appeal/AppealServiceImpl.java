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
import uz.technocorp.ecosystem.modules.appeal.processor.AppealPdfProcessor;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcess;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessRepository;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.equipmentappeal.dto.BoilerDto;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
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
    private final AppealRepository appealRepository;
    private final ProfileService profileService;
    private final RegionService regionService;
    private final DistrictService districtService;
    private final OfficeRepository officeRepository;
    private final DocumentService documentService;
    private final TemplateService templateService;
    private final AttachmentService attachmentService;
    private final AppealExecutionProcessRepository appealExecutionProcessRepository;

    private final Map<Class<? extends AppealDto>, AppealPdfProcessor> processors;

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
        appeal.setResolution(dto.resolution());
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
        Profile profile = profileService.getProfile(user.getProfileId());
        Region region = regionService.getById(dto.getRegionId());
        District district = districtService.getDistrict(dto.getDistrictId());
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
                .regionId(dto.getRegionId())
                .legalDistrictId(profile.getDistrictId())
                .districtId(dto.getDistrictId())
                .officeId(office.getId())
                .officeName(office.getName())
                .status(AppealStatus.NEW)
                .address(region.getName()+", "+district.getName()+", "+dto.getAddress())
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
    public String preparePdfWithParam(AppealDto dto, User user) {
        AppealPdfProcessor processor = processors.get(dto.getClass());
        if (processor == null) {
            throw new ResourceNotFoundException("Form obyekt turi xato: " + dto.getClass().getSimpleName());
        }
        return processor.preparePdfWithParam(dto, user);
    }

    @Override
    public String prepareReplyPdfWithParam(User user, ReplyDto replyDto) {
        Template template = templateService.getByType(TemplateType.REPLY_APPEAL.name());

        Appeal appeal = repository.findById(replyDto.getAppealId()).orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", replyDto.getAppealId()));

        // Current date
        String[] formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.of("uz"))).split(" ");

        // Collect params to Map
        Map<String, String> parameters = new HashMap<>();
        parameters.put("day", formattedDate[0]);
        parameters.put("month", formattedDate[1]);
        parameters.put("year", formattedDate[2]);
        parameters.put("officeName", appeal.getOfficeName());
        parameters.put("inspectorName", user.getName());
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("legalTin", appeal.getLegalTin().toString());
        parameters.put("address", appeal.getAddress());
        parameters.put("name", appeal.getData().get("name").asText());
        parameters.put("upperOrganization", appeal.getData().get("upperOrganization").asText());
        parameters.put("appealType", appeal.getAppealType().getLabel());
        parameters.put("extraArea", appeal.getData().get("extraArea").asText());
        parameters.put("hazardousSubstance", appeal.getData().get("hazardousSubstance").asText());
        parameters.put("conclusion", replyDto.getConclusion());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply", parameters);
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
    @Transactional
    public void saveReplyAndSign(User user, SignedReplyDto dto, HttpServletRequest request) {
        // Check and get appeal by ID
        Appeal appeal = repository.findById(dto.getDto().getAppealId()).orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", dto.getDto().getAppealId()));

        // Set conclusion
        repository.setConclusion(appeal.getId(), dto.getDto().getConclusion());

        // Create a document
        documentService.create(new DocumentDto(dto.getType(), appeal.getId(), dto.getFilePath(), dto.getSign(), Helper.getIp(request), user.getId()));
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
}
