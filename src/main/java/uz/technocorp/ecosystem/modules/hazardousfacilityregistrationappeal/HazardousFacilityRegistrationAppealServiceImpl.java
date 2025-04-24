//package uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
//import uz.technocorp.ecosystem.modules.appeal.Appeal;
//import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
//import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
//import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
//import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
//import uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.helper.HazardousFacilityRegistrationAppealHelper;
//import uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.projection.HazardousFacilityRegistrationAppealView;
//import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessRepository;
//import uz.technocorp.ecosystem.modules.appealexecutionprocess.projection.AppealExecutionProcessProjection;
//import uz.technocorp.ecosystem.modules.attachment.Attachment;
//import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
//import uz.technocorp.ecosystem.modules.document.DocumentRepository;
//import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;
//import uz.technocorp.ecosystem.modules.office.Office;
//import uz.technocorp.ecosystem.modules.office.OfficeRepository;
//import uz.technocorp.ecosystem.modules.profile.Profile;
//import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
//import uz.technocorp.ecosystem.modules.user.User;
//import uz.technocorp.ecosystem.publics.AttachmentDto;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Objects;
//import java.util.UUID;
//
///**
// * @author Rasulov Komil
// * @version 1.0
// * @created 12.02.2025
// * @since v1.0
// */
//@Service
//@RequiredArgsConstructor
//public class HazardousFacilityRegistrationAppealServiceImpl implements HazardousFacilityRegistrationAppealService {
//
//    private final HazardousFacilityRegistrationAppealRepository repository;
//    private final AppealRepository appealRepository;
//    private final ProfileRepository profileRepository;
//    private final AttachmentRepository attachmentRepository;
//    private final DocumentRepository documentRepository;
//    private final AppealExecutionProcessRepository appealExecutionProcessRepository;
//    private final OfficeRepository officeRepository;
//
//    @Override
//    @Transactional
//    public void create(User user, HfAppealDto dto) {
//
//        Profile profile = profileRepository
//                .findById(user.getProfileId())
//                .orElseThrow(() -> new ResourceNotFoundException("Profil", "Id", user.getProfileId()));
//
//        Attachment identificationAttachment = attachmentRepository
//                .findByPath(dto.identificationCardPath())
//                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.identificationCardPath()));
//        Attachment receiptAttachment = attachmentRepository
//                .findByPath(dto.receiptPath())
//                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.receiptPath()));
//        Integer maxOrderNumber = repository.findMaxOrderNumber();
//        Integer orderNumber = (maxOrderNumber != null ? maxOrderNumber : 0) + 1;
//        HazardousFacilityRegistrationAppeal hazardousFacilityRegistrationAppeal = repository.save(
//                HazardousFacilityRegistrationAppeal.builder()
//                        .appealType(AppealType.valueOf(dto.appealType()))
//                        .number("Special symbol by appealType" + orderNumber)
//                        .orderNumber(orderNumber)
//                        .legalTin(profile.getTin())
//                        .legalName(profile.getLegalName())
//                        .regionId(dto.regionId())
//                        .districtId(dto.districtId())
//                        .profileId(profile.getId())
//                        .legalAddress(profile.getLegalAddress())
//                        .phoneNumber(dto.phoneNumber())
//                        .email(dto.email())
//                        .upperOrganization(dto.upperOrganization())
//                        .name(dto.name())
//                        .address(dto.address())
//                        .hazardousFacilityTypeId(dto.hazardousFacilityTypeId())
//                        .extraArea(dto.extraArea())
//                        .description(dto.description())
//                        .identificationCardPath(dto.identificationCardPath())
//                        .receiptPath(dto.receiptPath())
//                        .build()
//        );
//        Office office = officeRepository
//                .findById(profile.getOfficeId())
//                .orElseThrow(() -> new ResourceNotFoundException("Office", "Id", profile.getOfficeId()));
//        Appeal appeal = appealRepository.save(
//                Appeal.builder()
//                        .appealType(AppealType.valueOf(dto.appealType()))
//                        .number("Special symbol by appealType" + orderNumber)
//                        .legalTin(profile.getTin())
//                        .legalName(profile.getLegalName())
//                        .regionId(profile.getRegionId())
//                        .regionName(profile.getRegionName())
//                        .districtId(profile.getDistrictId())
//                        .districtName(profile.getDistrictName())
//                        .officeId(office.getId())
//                        .officeName(office.getName())
//                        .phoneNumber(dto.phoneNumber())
//                        .date(LocalDate.now())
//                        .address(dto.address())
//                        .status(AppealStatus.NEW)
//                        .build()
//        );
//        hazardousFacilityRegistrationAppeal.setAppealId(appeal.getId());
//        repository.save(hazardousFacilityRegistrationAppeal);
//        attachmentRepository.deleteAllById(List.of(identificationAttachment.getId(), receiptAttachment.getId()));
//    }
//
//    @Override
//    public void update(UUID id, HfAppealDto dto) {
//        HazardousFacilityRegistrationAppeal hazardousFacilityRegistrationAppeal = repository
//                .findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
//        hazardousFacilityRegistrationAppeal.setHazardousFacilityTypeId(dto.hazardousFacilityTypeId());
//        hazardousFacilityRegistrationAppeal.setAddress(dto.address());
//        hazardousFacilityRegistrationAppeal.setEmail(dto.email());
//        hazardousFacilityRegistrationAppeal.setDescription(dto.description());
//        hazardousFacilityRegistrationAppeal.setExtraArea(dto.extraArea());
//        hazardousFacilityRegistrationAppeal.setUpperOrganization(dto.upperOrganization());
//        hazardousFacilityRegistrationAppeal.setName(dto.name());
//        hazardousFacilityRegistrationAppeal.setPhoneNumber(dto.phoneNumber());
//        hazardousFacilityRegistrationAppeal.setAppealType(AppealType.valueOf(dto.appealType()));
//        hazardousFacilityRegistrationAppeal.setOrderNumber(1);
//        Appeal appeal = appealRepository
//                .findById(hazardousFacilityRegistrationAppeal.getAppealId())
//                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", hazardousFacilityRegistrationAppeal.getAppealId()));
//        appeal.setAppealType(AppealType.valueOf(dto.appealType()));
//        if (!Objects.equals(appeal.getRegionId(), dto.regionId())) {
//            hazardousFacilityRegistrationAppeal.setRegionId(dto.regionId());
//        }
//        if (!Objects.equals(appeal.getDistrictId(), dto.districtId())) {
//            hazardousFacilityRegistrationAppeal.setDistrictId(dto.districtId());
//        }
//        appealRepository.save(appeal);
//        repository.save(hazardousFacilityRegistrationAppeal);
//    }
//
//    @Override
//    public HazardousFacilityRegistrationAppealHelper getById(UUID id) {
//        HazardousFacilityRegistrationAppealView hazardousFacilityRegistrationAppealView = repository
//                .getFullInfoById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", id));
//        List<AppealExecutionProcessProjection> executionProcessList = appealExecutionProcessRepository.getAllByAppealId(hazardousFacilityRegistrationAppealView.getAppealId());
//        List<DocumentProjection> documentProjections = documentRepository.getByAppealId(hazardousFacilityRegistrationAppealView.getAppealId());
//        return new HazardousFacilityRegistrationAppealHelper(hazardousFacilityRegistrationAppealView, executionProcessList, documentProjections);
//
//    }
//
//    @Override
//    public void setAttachments(AttachmentDto dto) {
//
//        HazardousFacilityRegistrationAppeal hazardousFacilityRegistrationAppeal = repository
//                .findById(dto.objectId())
//                .orElseThrow(() -> new ResourceNotFoundException("Xicho arizasi", "Id", dto.objectId()));
//        Attachment attachment = attachmentRepository
//                .findByPath(dto.path())
//                .orElseThrow(() -> new ResourceNotFoundException("Fayl", "havola", dto.path()));
//
//        switch (dto.attachmentName()) {
//            case "identificationCardPath" -> hazardousFacilityRegistrationAppeal.setIdentificationCardPath(dto.path());
//            case "receiptPath" -> hazardousFacilityRegistrationAppeal.setReceiptPath(dto.path());
//            case "licensePath" -> hazardousFacilityRegistrationAppeal.setLicensePath(dto.path());
//            case "appointmentOrderPath" -> hazardousFacilityRegistrationAppeal.setAppointmentOrderPath(dto.path());
//            case "cadastralPassportPath" -> hazardousFacilityRegistrationAppeal.setCadastralPassportPath(dto.path());
//            case "certificationPath" -> hazardousFacilityRegistrationAppeal.setCertificationPath(dto.path());
//            case "deviceTestingPath" -> hazardousFacilityRegistrationAppeal.setDeviceTestingPath(dto.path());
//            case "ecologicalConclusionPath" ->
//                    hazardousFacilityRegistrationAppeal.setEcologicalConclusionPath(dto.path());
//            case "expertOpinionPath" -> hazardousFacilityRegistrationAppeal.setExpertOpinionPath(dto.path());
////            case "fireSafetyReportPath" -> appealDangerousObject.setFireSafetyReportPath(dto.path());
//            case "industrialSafetyDeclarationPath" ->
//                    hazardousFacilityRegistrationAppeal.setIndustrialSafetyDeclarationPath(dto.path());
//            case "insurancePolicyPath" -> hazardousFacilityRegistrationAppeal.setInsurancePolicyPath(dto.path());
//            case "permitPath" -> hazardousFacilityRegistrationAppeal.setPermitPath(dto.path());
//            case "projectDocumentationPath" ->
//                    hazardousFacilityRegistrationAppeal.setProjectDocumentationPath(dto.path());
//            case "replyLetterPath" -> hazardousFacilityRegistrationAppeal.setReplyLetterPath(dto.path());
//            default -> throw new ResourceNotFoundException("Fayl nomi", "nomlanish", dto.attachmentName());
//        }
//        repository.save(hazardousFacilityRegistrationAppeal);
//        attachmentRepository.deleteById(attachment.getId());
//    }
//}
