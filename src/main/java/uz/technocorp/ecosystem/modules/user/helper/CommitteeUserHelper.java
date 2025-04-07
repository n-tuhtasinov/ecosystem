package uz.technocorp.ecosystem.modules.user.helper;

import java.util.List;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 03.04.2025
 * @since v1.0
 */

public record CommitteeUserHelper(
        UUID id,
        String fullName,
        Long pin,
        String role,
        List<String> directions,
        String department,
        Integer departmentId,
        String position,
        String phoneNumber,
        Boolean enabled
) {}
