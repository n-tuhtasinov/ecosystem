package uz.technocorp.ecosystem.modules.irs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.hf.view.HfPageView;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDeregisterDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.modules.irs.dto.IrsParams;
import uz.technocorp.ecosystem.modules.irs.enums.IrsCategory;
import uz.technocorp.ecosystem.modules.irs.enums.IrsIdentifierType;
import uz.technocorp.ecosystem.modules.irs.enums.IrsUsageType;
import uz.technocorp.ecosystem.modules.irs.view.IrsView;
import uz.technocorp.ecosystem.modules.irs.view.IrsViewById;
import uz.technocorp.ecosystem.modules.irsappeal.dto.IrsAppealDto;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.utils.JsonParser;

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
    private final OfficeService officeService;
    private final ProfileService profileService;
    private final RegionService regionService;

    @Override
    public void create(Appeal appeal) {

        Long maxOrderNumber = repository.findMaxOrderNumber().orElse(0L) + 1;
        Region region = regionService.findById(appeal.getRegionId());
        String registryNumber = String.format("%02d", region.getNumber()) + "-S-" + String.format("%04d", maxOrderNumber);
        IrsAppealDto irsAppealDto = JsonParser.parseJsonData(appeal.getData(), IrsAppealDto.class);

        repository.save(
                IonizingRadiationSource
                        .builder()
                        .profileId(appeal.getProfileId())
                        .legalTin(appeal.getLegalTin())
                        .legalName(appeal.getLegalName())
                        .legalAddress(appeal.getLegalAddress())
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
                        .acceptedAt(LocalDate.now())
                        .isValid(irsAppealDto.getIsValid())
                        .usageType(IrsUsageType.valueOf(irsAppealDto.getUsageType()))
                        .storageLocation(irsAppealDto.getStorageLocation())
                        .files(irsAppealDto.getFiles())
                        .regionId(appeal.getRegionId())
                        .districtId(appeal.getDistrictId())
                        .appealId(appeal.getId())
                        .registryNumber(registryNumber)
                        .registrationDate(LocalDate.now())
                        .build()
        );
    }


    @Override
    public void update(UUID id, IrsDto dto) {
        IonizingRadiationSource irs = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("INM", "Id", id));

        irs.setAddress(dto.getAddress());
        irs.setParentOrganization(dto.getParentOrganization());
        irs.setSupervisorName(dto.getSupervisorName());
        irs.setSupervisorEducation(dto.getSupervisorEducation());
        irs.setSupervisorStatus(dto.getSupervisorStatus());
        irs.setSupervisorPosition(dto.getSupervisorPosition());
        irs.setSupervisorPhoneNumber(dto.getSupervisorPhoneNumber());
        irs.setDivision(dto.getDivision());
        irs.setIdentifierType(IrsIdentifierType.valueOf(dto.getIdentifierType()));
        irs.setSymbol(dto.getSymbol());
        irs.setSphere(dto.getSphere());
        irs.setFactoryNumber(dto.getFactoryNumber());
        irs.setCategory(IrsCategory.valueOf(dto.getCategory()));
        irs.setType(dto.getType());
        irs.setCountry(dto.getCountry());
        irs.setManufacturedAt(LocalDate.parse(dto.getManufacturedAt()));
        irs.setAcceptedFrom(dto.getAcceptedFrom());
        irs.setIsValid(dto.getIsValid());
        irs.setUsageType(IrsUsageType.valueOf(dto.getUsageType()));
        irs.setStorageLocation(dto.getStorageLocation());
        irs.setFiles(dto.getFiles());
        irs.setRegionId(dto.getRegionId());
        irs.setDistrictId(dto.getDistrictId());
        repository.save(irs);
    }

    @Override
    public void deregister(UUID id, IrsDeregisterDto dto) {

    }

    @Override
    public Page<IrsView> getAll(User user, IrsParams params) {
        Profile profile = profileService.getProfile(user.getProfileId());

        //check by role
        if (user.getRole() == Role.INSPECTOR || user.getRole() == Role.REGIONAL) {
            Office office = officeService.findById(profile.getOfficeId());
            if (params.getRegionId() != null) {
                if (!params.getRegionId().equals(office.getRegionId())) {
                    throw new RuntimeException("Sizga bu viloyat ma'lumotlarini ko'rish uchun ruxsat berilmagan");
                }
            }
            params.setRegionId(office.getRegionId());
        } else if (user.getRole() == Role.LEGAL) {
            params.setLegalTin(profile.getTin());
        } else {
            //TODO zaruriyat bo'lsa boshqa rollar uchun logika yozish kerak
        }

        return repository.getAll(params);
    }

    @Override
    public IrsViewById getById(UUID irsId) {
        IonizingRadiationSource irs = repository.getIrsById(irsId).orElseThrow(() -> new ResourceNotFoundException("INM", "ID", irsId));
        return mapToView(irs);
    }

    private IrsViewById mapToView(IonizingRadiationSource irs) {
        return new IrsViewById(
                irs.getParentOrganization(),
                irs.getAddress(),
                irs.getSupervisorName(),
                irs.getSupervisorPosition(),
                irs.getSupervisorStatus(),
                irs.getSupervisorEducation(),
                irs.getSupervisorPhoneNumber(),
                irs.getDivision(),
                irs.getIdentifierType(),
                irs.getSymbol(),
                irs.getSphere(),
                irs.getFactoryNumber(),
                irs.getActivity(),
                irs.getType(),
                irs.getCategory(),
                irs.getCountry(),
                irs.getManufacturedAt(),
                irs.getAcceptedFrom(),
                irs.getAcceptedAt(),
                irs.getIsValid(),
                irs.getUsageType(),
                irs.getStorageLocation(),
                irs.getFiles(),
                irs.getAppealId(),
                irs.getRegistryNumber(),
                irs.getProfileId(),
                irs.getLegalTin(),
                irs.getRegistrationDate());
    }

    @Override
    public Page<HfPageView> getAllForRiskAssessment(User user, int page, int size, Long tin, String registryNumber, Boolean isAssigned, Integer intervalId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Role role = user.getRole();
        if (role == Role.REGIONAL) {
            Profile profile = profileService.getProfile(user.getProfileId());
            Office office = officeService.findById(profile.getOfficeId());
            Integer regionId = office.getRegionId();
            if (isAssigned) {
                if (registryNumber != null)
                    return repository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId);
                if (tin != null) return repository.getAllByLegalTinAndInterval(pageable, tin, intervalId);
                else return repository.getAllByRegionAndInterval(pageable, regionId, intervalId);
            } else {
                if (registryNumber != null) return repository.getAllByRegistryNumber(pageable, registryNumber, intervalId);
                if (tin != null) return repository.getAllByLegalTin(pageable, tin, intervalId);
                else return repository.getAllByRegion(pageable, regionId, intervalId);
            }
        } else if (role == Role.INSPECTOR) {
            if (registryNumber != null)
                return repository.getAllByRegistryNumberAndIntervalAndInspectorId(pageable, registryNumber, intervalId, user.getId());
            if (tin != null) return repository.getAllByLegalTinAndIntervalAndInspectorId(pageable, tin, intervalId, user.getId());
            else return repository.getAllByInspectorIdAndInterval(pageable, user.getId(), intervalId);
        } else {
            Profile profile = profileService.getProfile(user.getProfileId());
            Long profileTin = profile.getTin();
            if (registryNumber != null) return repository.getAllByRegistryNumberAndInterval(pageable, registryNumber, intervalId);
            return repository.getAllByLegalTinAndInterval(pageable, profileTin, intervalId);
        }


    }
}
