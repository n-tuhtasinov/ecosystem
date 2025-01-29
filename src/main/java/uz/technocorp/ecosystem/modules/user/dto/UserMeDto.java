package uz.technocorp.ecosystem.modules.user.dto;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public record UserMeDto(
        UUID id,
        String fullName,
        Long pin,
        String role
) {}
