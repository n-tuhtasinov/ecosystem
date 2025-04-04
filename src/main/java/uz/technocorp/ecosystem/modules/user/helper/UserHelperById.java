package uz.technocorp.ecosystem.modules.user.helper;

import java.util.List;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 03.04.2025
 * @since v1.0
 */
public record UserHelperById(
        UUID id,
        String fullName,
        Long pin,
        String role,
        List<String> directions,
        Integer departmentId,
        Integer officeId,
        String position,
        String phoneNumber,
        Boolean enabled
) {}
