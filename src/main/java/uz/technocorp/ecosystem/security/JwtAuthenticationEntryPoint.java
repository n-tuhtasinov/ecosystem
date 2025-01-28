package uz.technocorp.ecosystem.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("Siz ushbu manbaga kirish uchun avtorizatsiyadan o'tmagansiz. Message: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Siz ushbu manbaga kirish uchun avtorizatsiyadan o'tmagansiz");
    }
}
