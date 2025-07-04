package uz.technocorp.ecosystem.modules.accreditation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.AccreditationRejectionDto;
import uz.technocorp.ecosystem.modules.accreditation.dto.ExpertiseConclusionDto;
import uz.technocorp.ecosystem.modules.accreditation.view.AccreditationPageView;
import uz.technocorp.ecosystem.modules.accreditation.view.AccreditationView;
import uz.technocorp.ecosystem.modules.accreditation.view.ExpConclusionPageView;
import uz.technocorp.ecosystem.modules.accreditation.view.ExpConclusionsView;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.AccreditationAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpConclusionAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ExpendAccreditationAppealDto;
import uz.technocorp.ecosystem.modules.accreditationappeal.dto.ReAccreditationAppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

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

    Page<AccreditationPageView> getAccreditations(User user, int page, int size);

    AccreditationView getAccreditation(UUID id);

    Page<ExpConclusionPageView> getConclusions(User user, int page, int size);

    ExpConclusionsView getExpConclusion(UUID id);

    AccreditationAppealDto setProfileInfos(UUID profileId, AccreditationAppealDto appealDto);

    ReAccreditationAppealDto setProfileInfos(UUID profileId, ReAccreditationAppealDto appealDto);

    ExpendAccreditationAppealDto setProfileInfos(UUID profileId, ExpendAccreditationAppealDto appealDto);

    ExpConclusionAppealDto setProfileInfos(UUID profileId, ExpConclusionAppealDto appealDto);
}
