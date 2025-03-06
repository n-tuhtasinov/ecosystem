package uz.technocorp.ecosystem.modules.appealdangerousobject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appealdangerousobject.dto.AppealDangerousObjectDto;
import uz.technocorp.ecosystem.modules.appealdangerousobject.helper.AppealDangerousObjectInfo;
import uz.technocorp.ecosystem.modules.appealdangerousobject.projection.AppealDangerousObjectProjection;
import uz.technocorp.ecosystem.modules.applicationexecutionprocess.AppealExecutionProcessRepository;
import uz.technocorp.ecosystem.modules.applicationexecutionprocess.projection.AppealExecutionProcessProjection;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.document.DocumentRepository;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
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
public class AppealDangerousObjectServiceImpl implements AppealDangerousObjectService{

    private final AppealDangerousObjectRepository repository;
    private final AppealRepository appealRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final ProfileRepository profileRepository;
    private final AttachmentRepository attachmentRepository;
    private final DocumentRepository documentRepository;
    private final AppealExecutionProcessRepository appealExecutionProcessRepository;
    private final OfficeRepository officeRepository;

    @Override
    public void create(User user, AppealDangerousObjectDto dto) {

        Region region = regionRepository
                .findById(dto.regionId())
                .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", dto.regionId()));

        District district = districtRepository
                .findById(dto.districtId())
                .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", dto.districtId()));


        Profile profile = profileRepository
                .findById(user.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Profil", "Id", user.getProfileId()));

        Attachment identificationAttachment = attachmentRepository
                .findByPath(dto.identificationCardPath())
                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.identificationCardPath()));
        Attachment receiptAttachment = attachmentRepository
                .findByPath(dto.receiptPath())
                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.receiptPath()));

        AppealDangerousObject appealDangerousObject = repository.save(
                new AppealDangerousObject(
                        AppealType.valueOf(dto.appealType()),
                        dto.number(),
                        dto.orderNumber(),
                        profile.getTin(),
                        profile.getLegalName(),
                        dto.regionId(),
                        region.getName(),
                        dto.districtId(),
                        district.getName(),
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
                        appealDangerousObject.getId(),
                        dto.address(),
                        dto.email(),
                        dto.phoneNumber(),
                        LocalDate.now()
                )
        );
        appealDangerousObject.setAppealId(appeal.getId());
        repository.save(appealDangerousObject);
        attachmentRepository.deleteById(identificationAttachment.getId());
        attachmentRepository.deleteById(receiptAttachment.getId());
    }

    @Override
    public void update(UUID id, AppealDangerousObjectDto dto) {
        AppealDangerousObject appealDangerousObject = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
        appealDangerousObject.setDangerousObjectTypeId(dto.dangerousObjectTypeId());
        appealDangerousObject.setAddress(dto.address());
        appealDangerousObject.setEmail(dto.email());
        appealDangerousObject.setDescription(dto.description());
        appealDangerousObject.setObjectNumber(dto.objectNumber());
        appealDangerousObject.setExtraArea(dto.extraArea());
        appealDangerousObject.setUpperOrganization(dto.upperOrganization());
        appealDangerousObject.setName(dto.name());
        appealDangerousObject.setPhoneNumber(dto.phoneNumber());
        appealDangerousObject.setNumber(dto.number());
        appealDangerousObject.setAppealType(AppealType.valueOf(dto.appealType()));
        appealDangerousObject.setOrderNumber(dto.orderNumber());
        Appeal appeal = appealRepository
                .findById(appealDangerousObject.getAppealId())
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", appealDangerousObject.getAppealId()));
        appeal.setAppealType(AppealType.valueOf(dto.appealType()));
        appeal.setNumber(dto.number());
        appeal.setOrderNumber(dto.orderNumber());
        if (!Objects.equals(appeal.getRegionId(), dto.regionId())) {
            appeal.setRegionId(dto.regionId());
            appealDangerousObject.setRegionId(dto.regionId());
            Region region = regionRepository
                    .findById(dto.regionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Viloyat", "Id", dto.regionId()));
            appeal.setRegionName(region.getName());
            appealDangerousObject.setRegionName(region.getName());
        }
        if (!Objects.equals(appeal.getDistrictId(), dto.districtId())) {
            appeal.setDistrictId(dto.districtId());
            appealDangerousObject.setDistrictId(dto.districtId());
            District district = districtRepository
                    .findById(dto.regionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tuman", "Id", dto.districtId()));
            appeal.setDistrictName(district.getName());
            appealDangerousObject.setDistrictName(district.getName());
        }
        appealRepository.save(appeal);
        repository.save(appealDangerousObject);
    }

    @Override
    public AppealDangerousObjectInfo getById(UUID id) {
        AppealDangerousObjectProjection appealDangerousObjectProjection = repository
                .getFullInfoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
        List<AppealExecutionProcessProjection> executionProcessList = appealExecutionProcessRepository.getAllByAppealId(appealDangerousObjectProjection.getAppealId());
        List<DocumentProjection> documentProjections = documentRepository.getByAppealId(appealDangerousObjectProjection.getAppealId());
        return new AppealDangerousObjectInfo(appealDangerousObjectProjection, executionProcessList, documentProjections);

    }

    @Override
    public void setAttachments(AttachmentDto dto) {

        AppealDangerousObject appealDangerousObject = repository
                .findById(dto.objectId())
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", dto.objectId()));
        Attachment attachment = attachmentRepository
                .findByPath(dto.path())
                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.path()));

        switch (dto.attachmentName()) {
            case "identificationCardPath" -> appealDangerousObject.setIdentificationCardPath(dto.path());
            case "receiptPath" -> appealDangerousObject.setReceiptPath(dto.path());
            case "licensePath" -> appealDangerousObject.setLicensePath(dto.path());
            case "appointmentOrderPath" -> appealDangerousObject.setAppointmentOrderPath(dto.path());
            case "cadastralPassportPath" -> appealDangerousObject.setCadastralPassportPath(dto.path());
            case "certificationPath" -> appealDangerousObject.setCertificationPath(dto.path());
            case "deviceTestingPath" -> appealDangerousObject.setDeviceTestingPath(dto.path());
            case "ecologicalConclusionPath" -> appealDangerousObject.setEcologicalConclusionPath(dto.path());
            case "expertOpinionPath" -> appealDangerousObject.setExpertOpinionPath(dto.path());
//            case "fireSafetyReportPath" -> appealDangerousObject.setFireSafetyReportPath(dto.path());
            case "industrialSafetyDeclarationPath" -> appealDangerousObject.setIndustrialSafetyDeclarationPath(dto.path());
            case "insurancePolicyPath" -> appealDangerousObject.setInsurancePolicyPath(dto.path());
            case "permitPath" -> appealDangerousObject.setPermitPath(dto.path());
            case "projectDocumentationPath" -> appealDangerousObject.setProjectDocumentationPath(dto.path());
            case "replyLetterPath" -> appealDangerousObject.setReplyLetterPath(dto.path());
            default -> throw new ResourceNotFoundException("Fayl nomi", "nomlanish", dto.attachmentName());
        }
        repository.save(appealDangerousObject);
        attachmentRepository.deleteById(attachment.getId());
    }
}
