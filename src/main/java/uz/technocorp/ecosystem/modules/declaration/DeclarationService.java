package uz.technocorp.ecosystem.modules.declaration;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.declaration.dto.DeclarationParams;
import uz.technocorp.ecosystem.modules.declaration.view.DeclarationView;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
public interface DeclarationService {

    void create(Appeal appeal, String registryNumber);

    Page<DeclarationView> getAll(User user, DeclarationParams params);

    Declaration getById(UUID declarationId);
}
