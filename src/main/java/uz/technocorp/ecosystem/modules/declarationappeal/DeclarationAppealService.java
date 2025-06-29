package uz.technocorp.ecosystem.modules.declarationappeal;

import jakarta.servlet.http.HttpServletRequest;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto.ConfirmPassportDto;
import uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto.RejectPassportDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
public interface DeclarationAppealService {
    String generateConfirmationPdf(User user, ConfirmPassportDto confirmPassportDto);

    String generateRejectionPdf(User user, RejectPassportDto rejectPassportDto);

    void confirm(User user, SignedReplyDto<ConfirmPassportDto> replyDto, HttpServletRequest request);

    void reject(User user, SignedReplyDto<RejectPassportDto> replyDto, HttpServletRequest request);
}
