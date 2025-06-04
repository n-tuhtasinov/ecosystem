package uz.technocorp.ecosystem.modules.user.view;

import java.util.List;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 03.04.2025
 * @since v1.0
 */
public record UserViewById(
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
