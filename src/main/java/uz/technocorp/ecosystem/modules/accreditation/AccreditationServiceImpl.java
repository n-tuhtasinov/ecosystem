package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationRejectionDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.ExpertiseConclusionDto;
import uz.technocorp.ecosystem.modules.accreditation.enums.AccreditationType;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.user.User;

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

    private final AccreditationRepository accreditationRepository;
    private final AppealRepository appealRepository;
    private final DocumentService documentService;

    @Override
    public String generateCertificate(User user, AccreditationDto accreditationDto) {
        //TODO PDF generate qilish kerak.
        return "";
    }

    @Override
    @Transactional
    public void createAccreditation(User user, SignedReplyDto<AccreditationDto> dto, HttpServletRequest request) {
        Optional<Accreditation> optionalAccreditation = accreditationRepository
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
            Accreditation accreditation = accreditationRepository.save(
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
    public void createExpertiseConclusion(User user, SignedReplyDto<ExpertiseConclusionDto> conclusionDto, HttpServletRequest request) {
        Appeal appeal = appealRepository
                .findById(conclusionDto.getDto().getAppealId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ekspert xulosasi arizasi",
                        "ID",
                        conclusionDto.getDto().getAppealId()));
        accreditationRepository.save(
                Accreditation
                        .builder()
                        .type(AccreditationType.CONCLUSION)
                        .tin(conclusionDto.getDto().getTin())
                        .appealId(conclusionDto.getDto().getAppealId())
                        .submissionDate(conclusionDto.getDto().getSubmissionDate())
                        .monitoringLetterDate(conclusionDto.getDto().getMonitoringLetterDate())
                        .monitoringLetterNumber(conclusionDto.getDto().getMonitoringLetterNumber())
                        .expertiseObjectName(conclusionDto.getDto().getExpertiseObjectName())
                        .firstSymbolsGroup(conclusionDto.getDto().getFirstSymbolsGroup())
                        .secondSymbolsGroup(conclusionDto.getDto().getSecondSymbolsGroup())
                        .thirdSymbolsGroup(conclusionDto.getDto().getThirdSymbolsGroup())
                        .objectAddress(conclusionDto.getDto().getObjectAddress())
                        .regionId(conclusionDto.getDto().getRegionId())
                        .districtId(conclusionDto.getDto().getDistrictId())
                        .expertiseConclusionPath(conclusionDto.getDto().getExpertiseConclusionPath())
                        .expertiseConclusionNumber(conclusionDto.getDto().getExpertiseConclusionNumber())
                        .expertTin(appeal.getLegalTin())
                        .build()
        );
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
}
