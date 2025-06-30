package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationRejectionDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.ExpertiseConclusionDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
public interface AccreditationService {

    String generateCertificate(User user, AccreditationDto accreditationDto);

    void notConfirmed(User user, @Valid SignedReplyDto<AccreditationRejectionDto> dto, HttpServletRequest request, boolean rejected);

    void createAccreditation(User user, SignedReplyDto<AccreditationDto> accreditationDto, HttpServletRequest request);
    void createExpertiseConclusion(User user, SignedReplyDto<ExpertiseConclusionDto> conclusionDto, HttpServletRequest request);
}
