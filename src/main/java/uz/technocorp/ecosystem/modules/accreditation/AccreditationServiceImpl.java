package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationParamsDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationRejectionDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.ConclusionReplyDto;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationType;
import uz.technocorp.ecosystem.modules.accreditation.view.AccreditationPageView;
import uz.technocorp.ecosystem.modules.accreditation.view.AccreditationView;
import uz.technocorp.ecosystem.modules.accreditation.view.ExpConclusionPageView;
import uz.technocorp.ecosystem.modules.accreditation.view.ExpConclusionsView;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.AccreditationAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpConclusionAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpendAccreditationAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ReAccreditationAppealDto;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AccreditationServiceImpl implements AccreditationService {

    private final AttachmentService attachmentService;
    private final AccreditationRepository repository;
    private final AppealRepository appealRepository;
    private final DocumentService documentService;
    private final ProfileService profileService;
    private final AppealService appealService;

    @Override
    public Page<AccreditationPageView> getAccreditations(User user, AccreditationParamsDto dto) {
        if (!List.of(Role.HEAD, Role.CHAIRMAN, Role.MANAGER).contains(user.getRole())) {
            throw new CustomException("Sizda Akkreditatsiyani ko'rish huquqi yo'q");
        }
        Long tin = parseTin(dto.getSearch());

        String legalName = null;
        if (tin == null) legalName = dto.getSearch();

        /*Specification<Accreditation> search = (root, cq, cb) -> {
            return
        }*/


        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return repository.getAllAccreditations(pageable, AccreditationType.ACCREDITATION, tin, legalName);
    }

    @Override
    public Page<?> getConclusions(User user, AccreditationParamsDto dto) {
        return null;
    }

    @Override
    public String generateCertificate(User user, AccreditationDto accreditationDto) {
        //TODO PDF generate qilish kerak.
        return "/files/registry-files/2025/july/4/1751621730971.pdf";
    }

    @Override
    @Transactional
    public void createAccreditation(User user, SignedReplyDto<AccreditationDto> dto, HttpServletRequest request) {
        Optional<Accreditation> optionalAccreditation = repository
                .findByCertificateNumber(dto.getDto().getCertificateNumber());
        Appeal appeal = appealRepository
                .findById(dto.getDto().getAppealId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Akkreditatsiya arizasi",
                        "ID",
                        dto.getDto().getAppealId()));
        UUID accreditationId;
        if (optionalAccreditation.isPresent()) {
            Accreditation accreditation = optionalAccreditation.get();
            if (!accreditation.getTin().equals(appeal.getLegalTin())) {
                throw new RuntimeException("Ariza begona tashkilot tomonidan yuborilgan!");
            }
            accreditation.setCertificateNumber(dto.getDto().getCertificateNumber());
            accreditation.setAccreditationSpheres(dto.getDto().getAccreditationSpheres());
            accreditation.setAppealId(dto.getDto().getAppealId());
            accreditation.setAccreditationCommissionDecisionNumber(dto.getDto().getAccreditationCommissionDecisionNumber());
            accreditation.setAccreditationCommissionDecisionDate(dto.getDto().getAccreditationCommissionDecisionDate());
            accreditation.setAccreditationCommissionDecisionPath(dto.getDto().getAccreditationCommissionDecisionPath());
            accreditation.setCertificateDate(dto.getDto().getCertificateDate());
            accreditation.setCertificateValidityDate(dto.getDto().getCertificateValidityDate());
            accreditation.setAssessmentCommissionDecisionDate(dto.getDto().getAssessmentCommissionDecisionDate());
            accreditation.setAssessmentCommissionDecisionPath(dto.getDto().getAssessmentCommissionDecisionPath());
            accreditation.setAssessmentCommissionDecisionNumber(dto.getDto().getAssessmentCommissionDecisionNumber());
            accreditation.setReferencePath(dto.getDto().getReferencePath());
            accreditationId = accreditation.getId();
        } else {
            Accreditation accreditation = repository.save(
                    Accreditation
                            .builder()
                            .accreditationSpheres(dto.getDto().getAccreditationSpheres())
                            .accreditationCommissionDecisionDate(dto.getDto().getAccreditationCommissionDecisionDate())
                            .accreditationCommissionDecisionNumber(dto.getDto().getAccreditationCommissionDecisionNumber())
                            .accreditationCommissionDecisionPath(dto.getDto().getAccreditationCommissionDecisionPath())
                            .assessmentCommissionDecisionPath(dto.getDto().getAssessmentCommissionDecisionPath())
                            .assessmentCommissionDecisionDate(dto.getDto().getAssessmentCommissionDecisionDate())
                            .assessmentCommissionDecisionNumber(dto.getDto().getAssessmentCommissionDecisionNumber())
                            .certificateDate(dto.getDto().getCertificateDate())
                            .certificateNumber(dto.getDto().getCertificateNumber())
                            .certificateValidityDate(dto.getDto().getCertificateValidityDate())
                            .referencePath(dto.getDto().getReferencePath())
                            .appealId(dto.getDto().getAppealId())
                            .tin(appeal.getLegalTin())
                            .type(AccreditationType.ACCREDITATION)
                            .build()
            );
            accreditationId = accreditation.getId();
        }
        documentService.create(
                new DocumentDto(
                        accreditationId,
                        DocumentType.ACCREDITATION_CERTIFICATE,
                        dto.getFilePath(),
                        dto.getSign(),
                        Helper.getIp(request),
                        user.getId(),
                        List.of(user.getId()),
                        AgreementStatus.APPROVED
                )
        );
        appeal.setStatus(AppealStatus.COMPLETED);
        appealRepository.save(appeal);
    }

    @Override
    public AccreditationView getAccreditation(UUID id) {
        return repository
                .getAccreditation(id)
                .orElseThrow(() -> new ResourceNotFoundException("Akkreditatsiya ma'lumoti", "ID", id));
    }

    @Override
    public Page<ExpConclusionPageView> getConclusions(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return repository.getAllExpertiseConclusions(pageable, AccreditationType.CONCLUSION.name());

    }

    @Override
    public ExpConclusionsView getExpConclusion(UUID id) {
        return repository
                .getExpertiseConclusion(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ekspertiza xulosasi ma'lumoti", "ID", id));
    }

    @Override
    public AccreditationAppealDto setProfileInfo(UUID profileId, AccreditationAppealDto appealDto) {
        Profile profile = profileService.getProfile(profileId);
        appealDto.setPhoneNumber(profile.getPhoneNumber());
        appealDto.setRegionId(profile.getRegionId());
        appealDto.setDistrictId(profile.getDistrictId());
        String address = profile.getLegalAddress().split(", ")[2];
        appealDto.setAddress(address);
        return appealDto;
    }

    @Override
    public ReAccreditationAppealDto setProfileInfo(UUID profileId, ReAccreditationAppealDto appealDto) {
        Profile profile = profileService.getProfile(profileId);
        appealDto.setPhoneNumber(profile.getPhoneNumber());
        appealDto.setRegionId(profile.getRegionId());
        appealDto.setDistrictId(profile.getDistrictId());
        return appealDto;
    }

    @Override
    public ExpendAccreditationAppealDto setProfileInfo(UUID profileId, ExpendAccreditationAppealDto appealDto) {
        Profile profile = profileService.getProfile(profileId);
        appealDto.setPhoneNumber(profile.getPhoneNumber());
        appealDto.setRegionId(profile.getRegionId());
        appealDto.setDistrictId(profile.getDistrictId());
        return appealDto;
    }

    @Override
    public void setProfileInfo(UUID profileId, ExpConclusionAppealDto appealDto) {

        Profile profile = profileService.getProfile(profileId);
        Accreditation accreditation = findAccreditationByTinAndType(profile, AccreditationType.ACCREDITATION);

        appealDto.setCertificateDate(accreditation.getCertificateDate());
        appealDto.setCertificateNumber(accreditation.getCertificateNumber());
        appealDto.setCertificateValidityDate(accreditation.getCertificateValidityDate());
        appealDto.setAccreditationSpheres(accreditation.getAccreditationSpheres());
    }

    @Override
    public String generateConclusionPdf(User user, ConclusionReplyDto dto) {
        return "/files/registry-files/2025/july/4/1751621730971.pdf";
    }

    @Override
    public void createConclusion(User user, SignedReplyDto<ConclusionReplyDto> signDto, HttpServletRequest request) {

        ConclusionReplyDto replyDto = signDto.getDto();
        Appeal appeal = appealService.findById(replyDto.appealId());
        ExpConclusionAppealDto dto = JsonParser.parseJsonData(appeal.getData(), ExpConclusionAppealDto.class);

        Long orderNumber = repository.getMaxNumber(AccreditationType.CONCLUSION).orElse(0L) + 1;
        String registryNumber = String.format("%03d", orderNumber) + "-EXP-" + LocalDate.now().getYear();

        repository.save(
                Accreditation
                        .builder()
                        .type(AccreditationType.CONCLUSION)
                        .customerTin(dto.getCustomerTin())
                        .customerFullName(dto.getCustomerFullName())
                        .customerLegalAddress(dto.getCustomerLegalAddress())
                        .customerLegalName(dto.getCustomerLegalName())
                        .customerLegalForm(dto.getCustomerLegalForm())
                        .customerPhoneNumber(dto.getCustomerPhoneNumber())
                        .appealId(appeal.getId())
                        .submissionDate(dto.getSubmissionDate())
                        .monitoringLetterDate(dto.getMonitoringLetterDate())
                        .monitoringLetterNumber(dto.getMonitoringLetterNumber())
                        .expertiseObjectName(dto.getObjectName())
                        .firstSymbolsGroup(dto.getFirstSymbolsGroup())
                        .secondSymbolsGroup(dto.getSecondSymbolsGroup())
                        .thirdSymbolsGroup(dto.getThirdSymbolsGroup())
                        .objectAddress(dto.getAddress())
                        .regionId(dto.getRegionId())
                        .districtId(dto.getDistrictId())
                        .expertiseConclusionPath(dto.getFiles().getOrDefault("expertiseConclusionPath", ""))
                        .expertiseConclusionNumber(dto.getExpertiseConclusionNumber())
                        .expertiseConclusionDate(LocalDate.now())
                        .tin(appeal.getLegalTin())
                        .orderNumber(orderNumber)
                        .registryNumber(registryNumber)
                        .build()
        );

        //create document
        documentService.create(new DocumentDto(appeal.getId(), DocumentType.REPLY_LETTER, signDto.getFilePath(), signDto.getSign(), Helper.getIp(request), user.getId(), List.of(user.getId()), null));

        //delete signed file from the attachment table
        attachmentService.deleteByPath(signDto.getFilePath());
    }

    @Override
    @Transactional
    public void notConfirmed(User user, SignedReplyDto<AccreditationRejectionDto> dto, HttpServletRequest request, boolean rejected) {
        Appeal appeal = appealRepository
                .findById(dto.getDto().getAppealId())
                .orElseThrow(() -> new ResourceNotFoundException("Akkreditatsiya arizasi", "ID", dto.getDto().getAppealId()));

        if (rejected) {
            documentService.create(
                    new DocumentDto(
                            dto.getDto().getAppealId(),
                            DocumentType.REPLY_LETTER,
                            dto.getFilePath(),
                            dto.getSign(),
                            Helper.getIp(request),
                            user.getId(),
                            List.of(user.getId()),
                            null
                    )
            );
            appeal.setStatus(AppealStatus.REJECTED);
        } else {
            documentService.create(
                    new DocumentDto(
                            dto.getDto().getAppealId(),
                            DocumentType.REPORT,
                            dto.getFilePath(),
                            dto.getSign(),
                            Helper.getIp(request),
                            user.getId(),
                            List.of(user.getId()),
                            null
                    )
            );
            appeal.setStatus(AppealStatus.REJECTED);
        }
        appealRepository.save(appeal);
    }

    private Accreditation findAccreditationByTinAndType(Profile profile, AccreditationType type) {
        return repository
                .findByTinAndType(profile.getTin(), type)
                .orElseThrow(() -> new ResourceNotFoundException("Akkreditatsiya tashkiloti", "STIR va tur", profile.getTin() + ", " + type));
    }

    private Long parseTin(String search) {
        try {
            return search.length() == 9 ? Long.parseLong(search) : null;
        } catch (RuntimeException ex) {
            return null;
        }
    }
}
