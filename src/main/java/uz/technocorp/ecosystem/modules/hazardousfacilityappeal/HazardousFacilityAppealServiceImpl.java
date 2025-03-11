package uz.technocorp.ecosystem.modules.hazardousfacilityappeal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.hazardousfacilityappeal.helper.HazardousFacilityAppealDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityappeal.projection.HazardousFacilityAppealView;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessRepository;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.projection.AppealExecutionProcessProjection;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.document.DocumentRepository;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.publics.AttachmentDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class HazardousFacilityAppealServiceImpl implements HazardousFacilityAppealService {

    private final HazardousFacilityAppealRepository repository;
    private final AppealRepository appealRepository;
    private final ProfileRepository profileRepository;
    private final AttachmentRepository attachmentRepository;
    private final DocumentRepository documentRepository;
    private final AppealExecutionProcessRepository appealExecutionProcessRepository;
    private final OfficeRepository officeRepository;

    @Override
    public void create(User user, uz.technocorp.ecosystem.modules.hazardousfacilityappeal.dto.HazardousFacilityAppealDto dto) {

        Profile profile = profileRepository
                .findById(user.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Profil", "Id", user.getProfileId()));

        Attachment identificationAttachment = attachmentRepository
                .findByPath(dto.identificationCardPath())
                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.identificationCardPath()));
        Attachment receiptAttachment = attachmentRepository
                .findByPath(dto.receiptPath())
                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.receiptPath()));

        HazardousFacilityAppeal hazardousFacilityAppeal = repository.save(
                new HazardousFacilityAppeal(
                        AppealType.valueOf(dto.appealType()),
                        dto.number(),
                        dto.orderNumber(),
                        profile.getTin(),
                        profile.getLegalName(),
                        dto.regionId(),
                        dto.districtId(),
                        user.getProfileId(),
                        profile.getLegalAddress(),
                        dto.phoneNumber(),
                        dto.email(),
                        dto.upperOrganization(),
                        dto.name(),
                        dto.address(),
                        dto.dangerousObjectTypeId(),
                        dto.extraArea(),
                        dto.description(),
                        dto.objectNumber(),
                        identificationAttachment.getPath(),
                        receiptAttachment.getPath()
                )
        );
        Office office = officeRepository
                .findById(profile.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office", "Id", profile.getOfficeId()));
        Appeal appeal = appealRepository.save(
                new Appeal(
                        AppealType.valueOf(dto.appealType()),
                        dto.number(),
                        dto.orderNumber(),
                        profile.getTin(),
                        profile.getLegalName(),
                        profile.getRegionId(),
                        profile.getRegionName(),
                        dto.districtId(),
                        profile.getDistrictName(),
                        user.getProfileId(),
                        profile.getOfficeId(),
                        office.getName(),
                        hazardousFacilityAppeal.getId(),
                        dto.address(),
                        dto.email(),
                        dto.phoneNumber(),
                        LocalDate.now()
                )
        );
        hazardousFacilityAppeal.setAppealId(appeal.getId());
        repository.save(hazardousFacilityAppeal);
        attachmentRepository.deleteById(identificationAttachment.getId());
        attachmentRepository.deleteById(receiptAttachment.getId());
    }

    @Override
    public void update(UUID id, uz.technocorp.ecosystem.modules.hazardousfacilityappeal.dto.HazardousFacilityAppealDto dto) {
        HazardousFacilityAppeal hazardousFacilityAppeal = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
        hazardousFacilityAppeal.setDangerousObjectTypeId(dto.dangerousObjectTypeId());
        hazardousFacilityAppeal.setAddress(dto.address());
        hazardousFacilityAppeal.setEmail(dto.email());
        hazardousFacilityAppeal.setDescription(dto.description());
        hazardousFacilityAppeal.setObjectNumber(dto.objectNumber());
        hazardousFacilityAppeal.setExtraArea(dto.extraArea());
        hazardousFacilityAppeal.setUpperOrganization(dto.upperOrganization());
        hazardousFacilityAppeal.setName(dto.name());
        hazardousFacilityAppeal.setPhoneNumber(dto.phoneNumber());
        hazardousFacilityAppeal.setNumber(dto.number());
        hazardousFacilityAppeal.setAppealType(AppealType.valueOf(dto.appealType()));
        hazardousFacilityAppeal.setOrderNumber(dto.orderNumber());
        Appeal appeal = appealRepository
                .findById(hazardousFacilityAppeal.getAppealId())
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", hazardousFacilityAppeal.getAppealId()));
        appeal.setAppealType(AppealType.valueOf(dto.appealType()));
        appeal.setNumber(dto.number());
        appeal.setOrderNumber(dto.orderNumber());
        if (!Objects.equals(appeal.getRegionId(), dto.regionId())) {
            hazardousFacilityAppeal.setRegionId(dto.regionId());
        }
        if (!Objects.equals(appeal.getDistrictId(), dto.districtId())) {
            hazardousFacilityAppeal.setDistrictId(dto.districtId());
        }
        appealRepository.save(appeal);
        repository.save(hazardousFacilityAppeal);
    }

    @Override
    public HazardousFacilityAppealDto getById(UUID id) {
        HazardousFacilityAppealView hazardousFacilityAppealView = repository
                .getFullInfoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
        List<AppealExecutionProcessProjection> executionProcessList = appealExecutionProcessRepository.getAllByAppealId(hazardousFacilityAppealView.getAppealId());
        List<DocumentProjection> documentProjections = documentRepository.getByAppealId(hazardousFacilityAppealView.getAppealId());
        return new HazardousFacilityAppealDto(hazardousFacilityAppealView, executionProcessList, documentProjections);

    }

    @Override
    public void setAttachments(AttachmentDto dto) {

        HazardousFacilityAppeal hazardousFacilityAppeal = repository
                .findById(dto.objectId())
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", dto.objectId()));
        Attachment attachment = attachmentRepository
                .findByPath(dto.path())
                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.path()));

        switch (dto.attachmentName()) {
            case "identificationCardPath" -> hazardousFacilityAppeal.setIdentificationCardPath(dto.path());
            case "receiptPath" -> hazardousFacilityAppeal.setReceiptPath(dto.path());
            case "licensePath" -> hazardousFacilityAppeal.setLicensePath(dto.path());
            case "appointmentOrderPath" -> hazardousFacilityAppeal.setAppointmentOrderPath(dto.path());
            case "cadastralPassportPath" -> hazardousFacilityAppeal.setCadastralPassportPath(dto.path());
            case "certificationPath" -> hazardousFacilityAppeal.setCertificationPath(dto.path());
            case "deviceTestingPath" -> hazardousFacilityAppeal.setDeviceTestingPath(dto.path());
            case "ecologicalConclusionPath" -> hazardousFacilityAppeal.setEcologicalConclusionPath(dto.path());
            case "expertOpinionPath" -> hazardousFacilityAppeal.setExpertOpinionPath(dto.path());
//            case "fireSafetyReportPath" -> appealDangerousObject.setFireSafetyReportPath(dto.path());
            case "industrialSafetyDeclarationPath" -> hazardousFacilityAppeal.setIndustrialSafetyDeclarationPath(dto.path());
            case "insurancePolicyPath" -> hazardousFacilityAppeal.setInsurancePolicyPath(dto.path());
            case "permitPath" -> hazardousFacilityAppeal.setPermitPath(dto.path());
            case "projectDocumentationPath" -> hazardousFacilityAppeal.setProjectDocumentationPath(dto.path());
            case "replyLetterPath" -> hazardousFacilityAppeal.setReplyLetterPath(dto.path());
            default -> throw new ResourceNotFoundException("Fayl nomi", "nomlanish", dto.attachmentName());
        }
        repository.save(hazardousFacilityAppeal);
        attachmentRepository.deleteById(attachment.getId());
    }
}
