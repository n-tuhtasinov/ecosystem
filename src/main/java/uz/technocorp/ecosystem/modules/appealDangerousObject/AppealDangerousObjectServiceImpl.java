package uz.technocorp.ecosystem.modules.appealDangerousObject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.configs.FileConfig;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appealDangerousObject.dto.AppealDangerousObjectDto;
import uz.technocorp.ecosystem.modules.appealDangerousObject.projection.AppealDangerousObjectProjection;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.publics.AttachmentDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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


        AppealDangerousObject appealDangerousObject = repository.save(
                new AppealDangerousObject(
                        dto.appealTypeId(),
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
                        dto.identificationCardId(),
                        dto.receiptId()
                )
        );
        Appeal appeal = appealRepository.save(
                new Appeal(
                        dto.appealTypeId(),
                        dto.number(),
                        dto.orderNumber(),
                        profile.getTin(),
                        profile.getLegalName(),
                        dto.regionId(),
                        region.getName(),
                        dto.districtId(),
                        district.getName(),
                        user.getProfileId(),
                        appealDangerousObject.getId()
                )
        );
        appealDangerousObject.setAppealId(appeal.getId());
        repository.save(appealDangerousObject);
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
        appealDangerousObject.setAppealTypeId(dto.appealTypeId());
        appealDangerousObject.setOrderNumber(dto.orderNumber());
        Appeal appeal = appealRepository
                .findById(appealDangerousObject.getAppealId())
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", appealDangerousObject.getAppealId()));
        appeal.setAppealTypeId(dto.appealTypeId());
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
    public AppealDangerousObjectProjection getById(UUID id) {
        return repository
                .getFullInfoById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Xicho arizasi", "Id", id));
    }

    @Override
    public void setAttachments(AttachmentDto dto, MultipartFile file) throws IOException {

        AppealDangerousObject appealDangerousObject = repository
                .findById(dto.objectId())
                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", dto.objectId()));

        Path path = FileConfig.path("Registry", "AppealDangerousObjects", file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(ResponseMessage.FILE_NOT_CREATED);
        }
        Attachment attachment = attachmentRepository.save(
                new Attachment(
                        path.toString().replace("\\", "/"),
                        file.getOriginalFilename(),
                        file.getContentType(),
                        file.getSize()
                )
        );
        switch (dto.attachmentName()) {
            case "identificationCardId" -> appealDangerousObject.setIdentificationCardId(attachment.getId());
            case "receiptId" -> appealDangerousObject.setReceiptId(attachment.getId());
            case "licenseId" -> appealDangerousObject.setLicenseId(attachment.getId());
            case "appointmentOrderId" -> appealDangerousObject.setAppointmentOrderId(attachment.getId());
            case "cadastralPassportId" -> appealDangerousObject.setCadastralPassportId(attachment.getId());
            case "certificationId" -> appealDangerousObject.setCertificationId(attachment.getId());
            case "deviceTestingId" -> appealDangerousObject.setDeviceTestingId(attachment.getId());
            case "ecologicalConclusionId" -> appealDangerousObject.setEcologicalConclusionId(attachment.getId());
            case "expertOpinionId" -> appealDangerousObject.setExpertOpinionId(attachment.getId());
            case "fireSafetyReportId" -> appealDangerousObject.setFireSafetyReportId(attachment.getId());
            case "industrialSafetyDeclarationId" -> appealDangerousObject.setIndustrialSafetyDeclarationId(attachment.getId());
            case "insurancePolicyId" -> appealDangerousObject.setInsurancePolicyId(attachment.getId());
            case "permitId" -> appealDangerousObject.setPermitId(attachment.getId());
            case "projectDocumentationId" -> appealDangerousObject.setProjectDocumentationId(attachment.getId());
            default -> throw new ResourceNotFoundException("Fayl nomi", "nomlanish", dto.attachmentName());
        }
        repository.save(appealDangerousObject);
    }
}
