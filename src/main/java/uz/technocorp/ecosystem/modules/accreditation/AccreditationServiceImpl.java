package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationRejectionDto;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;

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
    public void createAccreditation(User user, SignedReplyDto<AccreditationDto> accreditationDto, HttpServletRequest request) {
        Accreditation accreditation = accreditationRepository.save(
                Accreditation
                        .builder()
                        .accreditationSpheres(accreditationDto.getDto().getAccreditationSpheres())
                        .accreditationCommissionDecisionDate(accreditationDto.getDto().getAccreditationCommissionDecisionDate())
                        .accreditationCommissionDecisionNumber(accreditationDto.getDto().getAccreditationCommissionDecisionNumber())
                        .accreditationCommissionDecisionPath(accreditationDto.getDto().getAccreditationCommissionDecisionPath())
                        .assessmentCommissionDecisionPath(accreditationDto.getDto().getAssessmentCommissionDecisionPath())
                        .assessmentCommissionDecisionDate(accreditationDto.getDto().getAssessmentCommissionDecisionDate())
                        .assessmentCommissionDecisionNumber(accreditationDto.getDto().getAssessmentCommissionDecisionNumber())
                        .certificateDate(accreditationDto.getDto().getCertificateDate())
                        .certificateNumber(accreditationDto.getDto().getCertificateNumber())
                        .certificateValidityDate(accreditationDto.getDto().getCertificateValidityDate())
                        .referencePath(accreditationDto.getDto().getReferencePath())
                        .appealId(accreditationDto.getDto().getAppealId())
                        .build()
        );
        documentService.create(
                new DocumentDto(
                        accreditation.getId(),
                        DocumentType.ACCREDITATION_CERTIFICATE,
                        accreditationDto.getFilePath(),
                        accreditationDto.getSign(),
                        Helper.getIp(request),
                        user.getId(),
                        List.of(user.getId()),
                        AgreementStatus.APPROVED
                )
        );
    }

    @Override
    public void notConfirmed(User user, SignedReplyDto<AccreditationRejectionDto> dto, HttpServletRequest request, boolean rejected) {

    }
}
