package uz.technocorp.ecosystem.modules.cadastrepassport;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.cadastrepassport.dto.CadastrePassportParams;
import uz.technocorp.ecosystem.modules.cadastrepassport.view.CadastrePassportView;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
public interface CadastrePassportService {

    void create(Appeal appeal,String registryNumber);

    Page<CadastrePassportView> getAll(User user, CadastrePassportParams params);

    CadastrePassport getById(UUID passportId);
}
