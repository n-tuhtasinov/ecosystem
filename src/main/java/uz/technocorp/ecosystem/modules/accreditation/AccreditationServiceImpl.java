package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import uz.technocorp.ecosystem.modules.accreditation.view.AccreditationView;
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
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.template.Template;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AccreditationServiceImpl implements AccreditationService {

    private final AccreditationSpecification specification;
    private final AttachmentService attachmentService;
    private final AccreditationRepository repository;
    private final AppealRepository appealRepository;
    private final DocumentService documentService;
    private final TemplateService templateService;
    private final ProfileService profileService;
    private final AppealService appealService;
    private final RegionService regionService;
    private final DistrictService districtService;

    @Override
    public Page<AccreditationView> getAccreditations(User user, AccreditationParamsDto params) {
        if (!List.of(Role.HEAD, Role.CHAIRMAN, Role.MANAGER).contains(user.getRole())) {
            throw new CustomException("Sizda Akkreditatsiyani ko'rish huquqi yo'q");
        }
        // Search
        Specification<Accreditation> search = specification.search(params.getSearch());

        // Type
        Specification<Accreditation> type = specification.type(AccreditationType.ACCREDITATION);

        // Get pageRequest with sort (createdAt)
        PageRequest pageRequest = getPageRequest(params);

        // Get all
        Page<Accreditation> accreditations = repository.findAll(where(search).and(type), pageRequest);

        // Create PageImpl
        return new PageImpl<>(accreditations.stream().map(this::map).toList(), pageRequest, accreditations.getTotalElements());
    }

    @Override
    public Page<ExpConclusionsView> getConclusions(User user, AccreditationParamsDto params) {
        if (!List.of(Role.HEAD, Role.CHAIRMAN, Role.MANAGER).contains(user.getRole())) {
            throw new CustomException("Sizda Akkreditatsiya ekspertizasini ko'rish huquqi yo'q");
        }
        // Search
        Specification<Accreditation> search = specification.search(params.getSearch());

        // Type
        Specification<Accreditation> type = specification.type(AccreditationType.CONCLUSION);

        // Get pageRequest with sort (createdAt)
        PageRequest pageRequest = getPageRequest(params);

        // Get all
        Page<Accreditation> accreditations = repository.findAll(where(search).and(type), pageRequest);

        // Create PageImpl
        return new PageImpl<>(accreditations.stream().map(this::mapConclusion).toList(), pageRequest, accreditations.getTotalElements());
    }

    @Override
    public String generateCertificate(User user, AccreditationDto dto) {
        Appeal appeal = appealService.findById(dto.getAppealId());
        Profile profile = profileService.getProfile(appeal.getProfileId());

        Template template = templateService.getByType(TemplateType.ACCREDITATION_CERTIFICATE.name());

        // Certificate date
        String[] certArr = dto.getCertificateDate().format(DateTimeFormatter.ofPattern("yyyy MMMM dd", Locale.of("uz"))).split(" ");
        String certDate = certArr[0] + " yilning " + certArr[2] + " " + certArr[1];

        // Certificate validity date
        String[] certValidityArr = dto.getCertificateValidityDate().format(DateTimeFormatter.ofPattern("yyyy MMMM dd", Locale.of("uz"))).split(" ");
        String certValidDate = certValidityArr[0] + " yil " + certValidityArr[2] + " " + certValidityArr[1];

        Map<String, String> parameters = new HashMap<>();
        parameters.put("certificateDate", certDate);
        parameters.put("certificateNumber", dto.getCertificateNumber());
        parameters.put("certificateValidityDate", certValidDate);
        parameters.put("legalAddress", profile.getLegalAddress());
        parameters.put("legalName", profile.getLegalName());
        parameters.put("fullName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "reestr/accreditation/certificate", parameters, true);
    }

    @Override
    @Transactional
    public void createAccreditation(User user, SignedReplyDto<AccreditationDto> replyDto, HttpServletRequest request) {
        AccreditationDto dto = replyDto.getDto();

        Optional<Accreditation> accreditationOpl = repository.findByCertificateNumber(dto.getCertificateNumber());

        Appeal appeal = appealService.findById(dto.getAppealId());

        UUID accreditationId;
        if (accreditationOpl.isPresent()) {
            Accreditation acc = accreditationOpl.get();
            if (!acc.getTin().equals(appeal.getLegalTin())) {
                throw new RuntimeException("Ariza boshqa tashkilot tomonidan yuborilgan!");
            }
            acc.setCertificateNumber(dto.getCertificateNumber());
            acc.setAccreditationSpheres(dto.getAccreditationSpheres());
            acc.setAppealId(dto.getAppealId());
            acc.setAccreditationCommissionDecisionNumber(dto.getAccreditationCommissionDecisionNumber());
            acc.setAccreditationCommissionDecisionDate(dto.getAccreditationCommissionDecisionDate());
            acc.setAccreditationCommissionDecisionPath(dto.getAccreditationCommissionDecisionPath());
            acc.setCertificateDate(dto.getCertificateDate());
            acc.setCertificateValidityDate(dto.getCertificateValidityDate());
            acc.setAssessmentCommissionDecisionDate(dto.getAssessmentCommissionDecisionDate());
            acc.setAssessmentCommissionDecisionPath(dto.getAssessmentCommissionDecisionPath());
            acc.setAssessmentCommissionDecisionNumber(dto.getAssessmentCommissionDecisionNumber());
            acc.setReferencePath(dto.getReferencePath());
            acc.setAccreditationCertificatePath(dto.getAccreditationCertificatePath());

            accreditationId = repository.save(acc).getId();
        } else {
            Profile profile = profileService.findByTin(appeal.getLegalTin());

            accreditationId = repository.save(
                    Accreditation
                            .builder()
                            .accreditationSpheres(dto.getAccreditationSpheres())
                            .accreditationCommissionDecisionDate(dto.getAccreditationCommissionDecisionDate())
                            .accreditationCommissionDecisionNumber(dto.getAccreditationCommissionDecisionNumber())
                            .accreditationCommissionDecisionPath(dto.getAccreditationCommissionDecisionPath())
                            .assessmentCommissionDecisionPath(dto.getAssessmentCommissionDecisionPath())
                            .assessmentCommissionDecisionDate(dto.getAssessmentCommissionDecisionDate())
                            .assessmentCommissionDecisionNumber(dto.getAssessmentCommissionDecisionNumber())
                            .certificateDate(dto.getCertificateDate())
                            .certificateNumber(dto.getCertificateNumber())
                            .certificateValidityDate(dto.getCertificateValidityDate())
                            .referencePath(dto.getReferencePath())
                            .accreditationCertificatePath(dto.getAccreditationCertificatePath())
                            .appealId(dto.getAppealId())
                            .tin(profile.getTin())
                            .legalName(profile.getLegalName())
                            .legalAddress(profile.getLegalAddress())
                            .legalFullName(profile.getFullName())
                            .legalPhoneNumber(profile.getPhoneNumber())
                            .type(AccreditationType.ACCREDITATION)
                            .build()
            ).getId();
        }
        documentService.create(
                new DocumentDto(
                        accreditationId,
                        DocumentType.ACCREDITATION_CERTIFICATE,
                        replyDto.getFilePath(),
                        replyDto.getSign(),
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
    public AccreditationView getAccreditation(User user, UUID id) {
        return repository.findById(id).map(this::map).orElseThrow(() -> new ResourceNotFoundException("Akkreditatsiya" + "ID" + id));
    }

    @Override
    public ExpConclusionsView getExpConclusion(User user, UUID id) {
        return repository.findById(id).map(this::mapConclusion).orElseThrow(() -> new ResourceNotFoundException("Akkreditatsiya" + "ID" + id));
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
    public String generateConclusionPdf(User user, ConclusionReplyDto replyDto) {
        Appeal appeal = appealService.findById(replyDto.appealId());
        Template template = templateService.getByType(TemplateType.ACCREDITATION_CONCLUSION.name());

        // Certificate date
        String[] formatDate = appeal.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy MMMM dd", Locale.of("uz"))).split(" ");
        String date = formatDate[0] + " yil " + formatDate[2] + "-" + formatDate[1];

        String[] currentFormatDate = appeal.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy MMMM dd", Locale.of("uz"))).split(" ");
        String currentDate = currentFormatDate[0] + " yil " + currentFormatDate[2] + "-" + currentFormatDate[1];

        Map<String, String> parameters = new HashMap<>();
        parameters.put("legalName", appeal.getLegalName());
        parameters.put("date", date);
        parameters.put("registrationDate", currentDate);
        parameters.put("fullName", user.getName());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "reestr/accreditation/conclusion", parameters, true);
    }

    @Override
    public void createConclusion(User user, SignedReplyDto<ConclusionReplyDto> signDto, HttpServletRequest request) {

        ConclusionReplyDto replyDto = signDto.getDto();
        Appeal appeal = appealService.findById(replyDto.appealId());
        Profile profile = profileService.findByTin(appeal.getLegalTin());
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
                        .tin(profile.getTin())
                        .legalName(profile.getLegalName())
                        .legalAddress(profile.getLegalAddress())
                        .legalFullName(profile.getFullName())
                        .legalPhoneNumber(profile.getPhoneNumber())
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
        Appeal appeal = appealService.findById(dto.getDto().getAppealId());

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

    private PageRequest getPageRequest(AccreditationParamsDto params) {
        return PageRequest.of(params.getPage() - 1, params.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    // MAPPER
    private AccreditationView map(Accreditation acc) {
        AccreditationView view = new AccreditationView();

        view.setId(acc.getId());
        view.setAppealId(acc.getAppealId());
        view.setTin(acc.getTin());
        view.setLegalAddress(acc.getLegalAddress());
        view.setLegalName(acc.getLegalName());
        view.setPhoneNumber(acc.getLegalPhoneNumber());
        view.setFullName(acc.getLegalFullName());
        view.setAccreditationSpheres(acc.getAccreditationSpheres());
        view.setCertificateNumber(acc.getCertificateNumber());
        view.setCertificateDate(acc.getCertificateDate());
        view.setCertificateValidityDate(acc.getCertificateValidityDate());
        view.setAccreditationCommissionDecisionPath(acc.getAccreditationCommissionDecisionPath());
        view.setAssessmentCommissionDecisionPath(acc.getAssessmentCommissionDecisionPath());
        view.setReferencePath(acc.getReferencePath());
        view.setAccreditationCertificatePath(acc.getAccreditationCertificatePath());

        return view;
    }

    private ExpConclusionsView mapConclusion(Accreditation acc) {
        ExpConclusionsView view = new ExpConclusionsView();

        view.setId(acc.getId());
        view.setAppealId(acc.getAppealId());
        view.setCustomerLegalAddress(acc.getCustomerLegalAddress());
        view.setCustomerLegalName(acc.getCustomerLegalName());
        view.setCustomerFullName(acc.getCustomerFullName());
        view.setCustomerTin(acc.getCustomerTin());
        view.setSpheres(acc.getAccreditationSpheres());
        view.setExpertOrganizationName(acc.getLegalName());
        view.setExpertiseConclusionDate(acc.getExpertiseConclusionDate());
        view.setExpertiseConclusionNumber(acc.getExpertiseConclusionNumber());
        view.setExpertiseObjectName(acc.getExpertiseObjectName());
        view.setCustomerLegalForm(acc.getCustomerLegalForm());
        view.setExpertiseConclusionPath(acc.getExpertiseConclusionPath());
        view.setFirstSymbolsGroup(acc.getFirstSymbolsGroup());
        view.setSecondSymbolsGroup(acc.getSecondSymbolsGroup());
        view.setThirdSymbolsGroup(acc.getThirdSymbolsGroup());
        view.setObjectAddress(acc.getObjectAddress());

        return view;
    }
}
