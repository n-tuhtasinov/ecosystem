package uz.technocorp.ecosystem.modules.appeal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealStatusDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
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
    public UUID create(AppealDto dto, UUID profileId, String number) {

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ResourceNotFoundException("Profil", "ID", profileId));
        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", dto.getRegionId()));
        District district = districtRepository.findById(dto.getDistrictId()).orElseThrow(() -> new ResourceNotFoundException("Tuman", "ID", dto.getDistrictId()));
        Office office = officeRepository.findById(region.getOfficeId()).orElseThrow(() -> new ResourceNotFoundException("Office", "ID", region.getOfficeId()));
        String executorName = null;
        switch (dto.getAppealType()){
            case REGISTER_IRS, ACCEPT_IRS, PRESENT_IRS -> executorName = "INM ijrochi ismi"; //TODO: Ijrochi shaxs kimligini logikasini yozish kerak
            case ACCREDIT_EXPERT_ORGANIZATION -> executorName = "kimdir";
            case REGISTER_DECLARATION -> executorName = "yana kimdir"; // har bir ariza turi uchun yozish kerak
        }

        Appeal appeal = Appeal.builder()
                .appealType(dto.getAppealType())
                .number(number)
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
                .build();
        Appeal savedAppeal = repository.save(appeal);

        return savedAppeal.getId();
    }
}
