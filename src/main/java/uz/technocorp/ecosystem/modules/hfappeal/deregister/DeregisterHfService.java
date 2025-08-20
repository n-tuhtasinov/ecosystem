package uz.technocorp.ecosystem.modules.hfappeal.deregister;

import jakarta.servlet.http.HttpServletRequest;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.hfappeal.deregister.dto.HfDeregisterDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.08.2025
 * @since v1.0
 */
public interface DeregisterHfService {
    String deregisterPdf(User user, HfDeregisterDto dto);
    void deregister(User user, SignedAppealDto<HfDeregisterDto> signDto, HttpServletRequest request);
}
