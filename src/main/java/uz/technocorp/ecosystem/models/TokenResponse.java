package uz.technocorp.ecosystem.models;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}
