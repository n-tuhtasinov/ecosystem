package uz.technocorp.ecosystem.models;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 * @description custom template to return in the header of the response
 */
public record TokenResponse(
        String accessToken,
        String refreshToken
) {}
