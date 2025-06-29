package uz.technocorp.ecosystem.modules.cadastreappeal;

import uz.technocorp.ecosystem.modules.cadastreappeal.dto.ConfirmCadastreDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
public interface CadastreAppealService {
    String generateConfirmationPdf(User user, ConfirmCadastreDto confirmCadastreDto);
}
