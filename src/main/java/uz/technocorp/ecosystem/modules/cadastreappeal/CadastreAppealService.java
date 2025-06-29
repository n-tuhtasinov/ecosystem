package uz.technocorp.ecosystem.modules.cadastreappeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.ConfirmCadastreDto;
import uz.technocorp.ecosystem.modules.cadastreappeal.dto.RejectCadastreDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
public interface CadastreAppealService {
    String generateConfirmationPdf(User user, ConfirmCadastreDto confirmCadastreDto);

    String generateRejectionPdf(User user, RejectCadastreDto rejectCadastreDto);

    void confirm(User user, SignedReplyDto<ConfirmCadastreDto> replyDto, HttpServletRequest request);

    void reject(User user, SignedReplyDto<RejectCadastreDto> replyDto, HttpServletRequest request);
}
