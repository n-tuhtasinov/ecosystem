package uz.technocorp.ecosystem.modules.irs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDeregisterDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsRegistryDto;
import uz.technocorp.ecosystem.modules.irs.enums.IrsCategory;
import uz.technocorp.ecosystem.modules.irs.enums.IrsIdentifierType;
import uz.technocorp.ecosystem.modules.irs.enums.IrsUsageType;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAppealDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 30.04.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class IonizingRadiationSourceServiceImpl implements IonizingRadiationSourceService {

    private final IonizingRadiationSourceRepository repository;
    private final AppealRepository appealRepository;
    private final RegionRepository regionRepository;
    private final ProfileRepository profileRepository;

    @Override
    public void create(IrsRegistryDto dto) {
        Appeal appeal = appealRepository
                .findById(dto.appealId())
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", dto.appealId()));
        appeal.setStatus(AppealStatus.COMPLETED);
        appealRepository.save(appeal);
        Long maxOrderNumber = repository.findMaxOrderNumber().orElse(0L) + 1;
        Region region = regionRepository
                .findById(appeal.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", appeal.getRegionId()));
        String registryNumber = String.format("%02d", region.getNumber()) + "-S-" + String.format("%04d", maxOrderNumber);
        IrsAppealDto irsAppealDto = parseJsonData(appeal.getData());
        repository.save(
                IonizingRadiationSource
                        .builder()
                        .profileId(appeal.getProfileId())
                        .legalTin(appeal.getLegalTin())
                        .legalName(appeal.getLegalName())
                        .address(appeal.getAddress())
                        .parentOrganization(irsAppealDto.getParentOrganization())
                        .supervisorName(irsAppealDto.getSupervisorName())
                        .supervisorEducation(irsAppealDto.getSupervisorEducation())
                        .supervisorStatus(irsAppealDto.getSupervisorStatus())
                        .supervisorPosition(irsAppealDto.getSupervisorPosition())
                        .supervisorPhoneNumber(irsAppealDto.getSupervisorPhoneNumber())
                        .division(irsAppealDto.getDivision())
                        .identifierType(IrsIdentifierType.valueOf(irsAppealDto.getIdentifierType()))
                        .symbol(irsAppealDto.getSymbol())
                        .sphere(irsAppealDto.getSphere())
                        .factoryNumber(irsAppealDto.getFactoryNumber())
                        .orderNumber(maxOrderNumber)
                        .activity(irsAppealDto.getActivity())
                        .category(IrsCategory.valueOf(irsAppealDto.getCategory()))
                        .type(irsAppealDto.getType())
                        .country(irsAppealDto.getCountry())
                        .manufacturedAt(LocalDate.parse(irsAppealDto.getManufacturedAt()))
                        .acceptedFrom(irsAppealDto.getAcceptedFrom())
                        .isValid(irsAppealDto.getIsValid())
                        .usageType(IrsUsageType.valueOf(irsAppealDto.getUsageType()))
                        .storageLocation(irsAppealDto.getStorageLocation())
                        .passportPath(irsAppealDto.getPassportPath())
                        .additionalFilePath(irsAppealDto.getAdditionalFilePath())
                        .regionId(appeal.getRegionId())
                        .districtId(appeal.getDistrictId())
                        .appealId(appeal.getId())
                        .registryNumber(registryNumber)
                        .build()
        );
    }


    @Override
    public void update(UUID id, IrsDto dto) {
        IonizingRadiationSource irs = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("INM", "Id", id));
        irs.setAddress(dto.address());
        irs.setParentOrganization(dto.parentOrganization());
        irs.setSupervisorName(dto.supervisorName());
        irs.setSupervisorEducation(dto.supervisorEducation());
        irs.setSupervisorStatus(dto.supervisorStatus());
        irs.setSupervisorPosition(dto.supervisorPosition());
        irs.setSupervisorPhoneNumber(dto.supervisorPhoneNumber());
        irs.setDivision(dto.division());
        irs.setIdentifierType(IrsIdentifierType.valueOf(dto.identifierType()));
        irs.setSymbol(dto.symbol());
        irs.setSphere(dto.sphere());
        irs.setFactoryNumber(dto.factoryNumber());
        irs.setCategory(IrsCategory.valueOf(dto.category()));
        irs.setType(dto.type());
        irs.setCountry(dto.country());
        irs.setManufacturedAt(LocalDate.parse(dto.manufacturedAt()));
        irs.setAcceptedFrom(dto.acceptedFrom());
        irs.setIsValid(dto.isValid());
        irs.setUsageType(IrsUsageType.valueOf(dto.usageType()));
        irs.setStorageLocation(dto.storageLocation());
        irs.setPassportPath(dto.passportPath());
        irs.setAdditionalFilePath(dto.additionalFilePath());
        irs.setRegionId(dto.regionId());
        irs.setDistrictId(dto.districtId());
        repository.save(irs);
    }

    @Override
    public void deregister(UUID id, IrsDeregisterDto dto) {

    }

    private IrsAppealDto parseJsonData(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.treeToValue(jsonNode, IrsAppealDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON deserialization error", e);
        }
    }

    @Override
    public Page<HfPageView> getAllForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        UUID profileId = user.getProfileId();
        Profile profile = profileRepository
                .findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", profileId));
        Integer regionId = profile.getRegionId();
        if (isAssigned) {
            if (tin != null) return repository.getAllByLegalTinAndInterval(pageable, tin, intervalId);
            if (registryNumber != null) return repository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId);
            else return repository.getAllByRegionAndInterval(pageable, regionId, intervalId);
        } else {
            if (tin != null) return repository.getAllByLegalTin(pageable, tin);
            if (registryNumber != null) return repository.getAllByRegistryNumber(pageable, registryNumber);
            else return repository.getAllByRegion(pageable, regionId);
        }
    }
}
