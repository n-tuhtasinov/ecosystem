package uz.technocorp.ecosystem.modules.declaration;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.declaration.dto.DeclarationParams;
import uz.technocorp.ecosystem.modules.declaration.view.DeclarationView;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
public interface DeclarationRepo {
    Page<DeclarationView> getAllByParams(DeclarationParams params);
}
