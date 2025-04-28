package uz.technocorp.ecosystem.modules.appeal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealStatusDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SequenceNumberDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcess;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessRepository;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

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

    @Override
    @Transactional
    public void setInspector(SetInspectorDto dto) {
        User user = userRepository
                .findById(dto.inspectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Inspektor", "Id", dto.inspectorId()));
        Appeal appeal = repository
                .findById(dto.appealId())
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", dto.appealId()));
        appeal.setInspectorId(dto.inspectorId());
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
    public Page<AppealCustom> getAppealCustoms(Map<String, String> params) {
        return appealRepository.getAppealCustoms(params);
    }

    @Override
    public void create(AppealDto dto, User user) {

        Profile profile = profileRepository.findById(user.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profil", "ID", user.getProfileId()));
        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", dto.getRegionId()));
        District district = districtRepository.findById(dto.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("Tuman", "ID", dto.getDistrictId()));
        Office office = officeRepository.findById(region.getOfficeId()).orElseThrow(() -> new ResourceNotFoundException("Office", "ID", region.getOfficeId()));
        String executorName = getExecutorName(dto.getAppealType());
        SequenceNumberDto numberDto = makeNumber(dto.getAppealType());
        JsonNode data = makeJsonData(dto);

        Appeal appeal = Appeal
                .builder()
                .appealType(dto.getAppealType())
                .number(numberDto.number())
                .sequenceNumber(numberDto.sequenceNumber())
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
                .officeId(region.getOfficeId())
                .officeName(office.getName())
                .status(AppealStatus.NEW)
                .address(dto.getAddress())
                .legalAddress(profile.getLegalAddress())
                .phoneNumber(dto.getPhoneNumber())
                .deadline(dto.getDeadline())
                .date(LocalDate.now())
                .executorName(executorName)
                .data(data)
                .build();
        repository.save(appeal);
    }

    @Override
    public void update(UUID id, AppealDto dto) {
        Appeal appeal = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
        appeal.setData(makeJsonData(dto));
        repository.save(appeal);
    }

    private JsonNode makeJsonData(AppealDto dto) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.valueToTree(dto);
    }

    private SequenceNumberDto makeNumber(AppealType appealType) {
        Long orderNumber = appealRepository.getMax().orElse(0L) + 1;

        String number=null;

        switch (appealType){
            case REGISTER_IRS, ACCEPT_IRS, TRANSFER_IRS -> number = orderNumber + "-INM-" + LocalDate.now().getYear();
            case REGISTER_HF, DEREGISTER_HF -> number = orderNumber + "-XIC-" + LocalDate.now().getYear();
            // TODO: Ariza turiga qarab ariza raqamini shakllantirishni davom ettirish kerak
        }
        return new SequenceNumberDto(orderNumber, number);
    }

    private String getExecutorName(AppealType appealType) {
        String executorName = null;

        switch (appealType){
            case REGISTER_IRS, ACCEPT_IRS, TRANSFER_IRS -> executorName = "INM ijrochi ismi";
            case ACCREDIT_EXPERT_ORGANIZATION -> executorName = "kimdir";
            case REGISTER_DECLARATION -> executorName = "yana kimdir";
            //TODO: Ariza turiga qarab ariza ijrochi shaxs kimligini shakllantirishni davom ettirish kerak
        }
        return executorName;
    }
}
