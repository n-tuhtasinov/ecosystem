package uz.technocorp.ecosystem.modules.appeal.pdfservice;

import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.dto.ReplyAttestationDto;
import uz.technocorp.ecosystem.modules.appeal.dto.ReplyDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 20.06.2025
 * @since v1.0
 */
public interface AppealPdfService {

    String preparePdfWithParam(AppealDto dto, User user);

    String prepareReplyPdfWithParam(User user, ReplyDto replyDto);

    String prepareRegionalAcceptPdfWithParam(User user, SetInspectorDto inspectorDto);

    String prepareCommitteeAcceptPdfWithParam(User user, ReplyAttestationDto dto);

    String prepareRejectPdfWithParam(User user, ReplyDto replyDto);
}
