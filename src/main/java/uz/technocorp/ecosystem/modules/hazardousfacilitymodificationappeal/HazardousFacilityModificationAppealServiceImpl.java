package uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacilityRepository;
import uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal.dto.HazardousFacilityModificationAppealDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.HazardousFacilityRegistrationAppeal;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.03.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class HazardousFacilityModificationAppealServiceImpl implements HazardousFacilityModificationAppealService {

    private final HazardousFacilityModificationAppealRepository repository;
    private final HazardousFacilityRepository hazardousFacilityRepository;
    private final OfficeRepository officeRepository;
    private final AppealRepository appealRepository;
    private final ProfileRepository profileRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public void create(User user, HazardousFacilityModificationAppealDto dto) {

        Profile profile = profileRepository
                .findById(user.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Profil", "Id", user.getProfileId()));

        Attachment actAttachment = attachmentRepository
                .findByPath(dto.actPath())
                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.actPath()));
        Attachment appealAttachment = attachmentRepository
                .findByPath(dto.appealPath())
                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.appealPath()));
        HazardousFacility hazardousFacility = hazardousFacilityRepository
                .findById(dto.hazardousFacilityId())
                .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", dto.hazardousFacilityId()));
        Long maxOrderNumber = repository.findMaxOrderNumber();
        Long orderNumber = (maxOrderNumber != null ? maxOrderNumber : 0) + 1;


        HazardousFacilityModificationAppeal hazardousFacilityModificationAppeal = repository.save(
                HazardousFacilityModificationAppeal.builder()
                        .appealType(AppealType.valueOf(dto.appealType()))
                        .number("Special symbol by appealType" + orderNumber)
                        .orderNumber(orderNumber)
                        .hazardousFacilityId(dto.hazardousFacilityId())
                        .actPath(actAttachment.getPath())
                        .appealPath(appealAttachment.getPath())
                        .phoneNumber(dto.phoneNumber())
                        .email(dto.email())
                        .reason(dto.reason())
                        .statement(dto.statement())
                        .registryObjectNumber(hazardousFacility.getRegistryNumber())
                        .build()
        );
        Office office = officeRepository
                .findById(profile.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office", "Id", profile.getOfficeId()));
        Appeal appeal = appealRepository.save(
                Appeal.builder()
                        .appealType(AppealType.valueOf(dto.appealType()))
                        .number("Special symbol by appealType" + orderNumber)
                        .legalTin(profile.getTin())
                        .legalName(profile.getLegalName())
                        .regionId(profile.getRegionId())
                        .regionName(profile.getRegionName())
                        .districtId(profile.getDistrictId())
                        .districtName(profile.getDistrictName())
                        .profileId(user.getProfileId())
                        .officeId(office.getId())
                        .officeName(office.getName())
                        .phoneNumber(dto.phoneNumber())
                        .email(dto.email())
                        .date(LocalDate.now())
                        .address(hazardousFacility.getAddress())
                        .status(AppealStatus.NEW)
                        .build()
        );
        hazardousFacilityModificationAppeal.setAppealId(appeal.getId());
        repository.save(hazardousFacilityModificationAppeal);
        attachmentRepository.deleteAllById(List.of(appealAttachment.getId(), actAttachment.getId()));

    }

    @Override
    public void update(UUID id, HazardousFacilityModificationAppealDto dto) {
        HazardousFacilityModificationAppeal hazardousFacilityModificationAppeal = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", id));
        hazardousFacilityModificationAppeal.setAppealType(AppealType.valueOf(dto.appealType()));
        hazardousFacilityModificationAppeal.setStatement(dto.statement());
        hazardousFacilityModificationAppeal.setReason(dto.reason());
        hazardousFacilityModificationAppeal.setPhoneNumber(dto.phoneNumber());
        hazardousFacilityModificationAppeal.setEmail(dto.email());
        if (!hazardousFacilityModificationAppeal.getHazardousFacilityId().equals(dto.hazardousFacilityId())) {
            HazardousFacility hazardousFacility = hazardousFacilityRepository
                    .findById(dto.hazardousFacilityId())
                    .orElseThrow(() -> new ResourceNotFoundException("Xicho", "Id", dto.hazardousFacilityId()));
            hazardousFacilityModificationAppeal.setHazardousFacilityId(dto.hazardousFacilityId());
            hazardousFacilityModificationAppeal.setRegistryObjectNumber(hazardousFacility.getRegistryNumber());
        }
        repository.save(hazardousFacilityModificationAppeal);
    }
}
