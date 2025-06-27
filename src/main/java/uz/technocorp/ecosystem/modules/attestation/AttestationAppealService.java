package uz.technocorp.ecosystem.modules.attestation;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationPendingParamsDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 26.06.2025
 * @since v1.0
 */
public interface AttestationAppealService {

    Page<AppealViewById> getAllPending(User user, AttestationPendingParamsDto dto);
}
