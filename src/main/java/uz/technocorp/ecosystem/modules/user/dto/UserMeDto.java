package uz.technocorp.ecosystem.modules.user.dto;

import java.util.UUID;

public record UserMeDto(
        UUID id,
        String fullName,
        Long pin,
        String role
) {}
