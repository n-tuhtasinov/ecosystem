package uz.technocorp.ecosystem.modules.integration.iip;

import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 12.05.2025
 * @since v1.0
 */
public interface IIPService {
    String getToken();
    LegalUserDto getGnkInfo(String tin);
}
