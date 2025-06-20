package uz.technocorp.ecosystem.modules.attestation;


import jakarta.servlet.http.HttpServletRequest;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
public interface AttestationService {

    void create(User user, SignedAppealDto<AttestationDto> attestationDto, HttpServletRequest request);
}
