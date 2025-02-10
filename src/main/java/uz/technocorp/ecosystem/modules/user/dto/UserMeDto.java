package uz.technocorp.ecosystem.modules.user.dto;

import java.util.List;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public record UserMeDto(
        UUID id,
        String name,
        String role,
        List<String> directions
) {}
