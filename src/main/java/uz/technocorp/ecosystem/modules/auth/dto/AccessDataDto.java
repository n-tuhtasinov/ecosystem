package uz.technocorp.ecosystem.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessDataDto {
    private String scope;
    private String expires_in;
    private String token_type;
    private String refresh_token;
    private String access_token;
}
