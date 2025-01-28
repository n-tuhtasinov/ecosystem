package uz.technocorp.ecosystem.modules.auth;

import jakarta.servlet.http.HttpServletResponse;
import uz.technocorp.ecosystem.models.TokenResponse;
import uz.technocorp.ecosystem.modules.auth.dto.LoginDto;
import uz.technocorp.ecosystem.modules.auth.dto.OneIdDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;

public interface AuthService {

    UserMeDto login(LoginDto dto, HttpServletResponse response);

    UserMeDto loginViaOneId(OneIdDto dto, HttpServletResponse response);

    void setTokenToCookie(TokenResponse tokenResponse, HttpServletResponse response);

    void logout(HttpServletResponse response);
}
