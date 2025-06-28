package uz.technocorp.ecosystem.modules.attestation;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationConductDto;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationParamsDto;
import uz.technocorp.ecosystem.modules.attestation.view.AttestationView;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
public interface AttestationService {

    Page<?> getAllByParams(User user, AttestationParamsDto dto);

    AttestationView getById(User user, UUID attestationId);

    List<AttestationView> getByAppeal(User user, UUID appealId);

    void create(Appeal appeal);

    void conduct(User user, AttestationConductDto dto);
}
