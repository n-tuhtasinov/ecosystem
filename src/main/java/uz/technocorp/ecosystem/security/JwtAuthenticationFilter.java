package uz.technocorp.ecosystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.technocorp.ecosystem.modules.auth.AuthService;
import uz.technocorp.ecosystem.modules.auth.dto.AuthBasicDto;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.TokenResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    AuthService authService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            AuthBasicDto basic = getBasic(authHeader);

            UserDetails userDetails = userDetailsService.loadUserByUsername(basic.getUsername());

            // Check password
            if (!passwordEncoder.matches(basic.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Password is wrong");
            }
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
            return;
        }

        Map<String, String> map = getJwtFromCookie(request);
        if (map == null || map.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (map.containsKey(AppConstants.ACCESS_TOKEN)) {
            String accessToken = map.get(AppConstants.ACCESS_TOKEN);
            setAuthentication(request, accessToken);
        } else {
            if (map.containsKey(AppConstants.REFRESH_TOKEN)) {
                String refreshToken = map.get(AppConstants.REFRESH_TOKEN);
                setAuthentication(request, refreshToken);

                // update access and refresh token
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    String access = jwtService.generateToken(SecurityContextHolder.getContext().getAuthentication(), true);
                    String refresh = jwtService.generateToken(SecurityContextHolder.getContext().getAuthentication(), false);

                    authService.setTokenToCookie(new TokenResponse(access, refresh), response);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request, String token) {
        final String username = jwtService.extractUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }

    private Map<String, String> getJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(AppConstants.ACCESS_TOKEN)) {
                String accessToken = cookie.getValue();
                if (accessToken != null && !accessToken.isEmpty()) {
                    map.put(cookie.getName(), accessToken);
                }
            }

            if (cookie.getName().equals(AppConstants.REFRESH_TOKEN)) {
                String accessToken = cookie.getValue();
                if (accessToken != null && !accessToken.isEmpty()) {
                    map.put(cookie.getName(), accessToken);
                }
            }
        }
        return map;
    }

    private AuthBasicDto getBasic(String authHeader) {
        AuthBasicDto basic = new AuthBasicDto("", "");
        if (authHeader != null && !authHeader.isBlank()) {
            String decodedStr = new String((Base64.getDecoder().decode(authHeader.replace("Basic ", "").trim())));

            if (!decodedStr.isBlank()) {
                final String[] credentialsArray = decodedStr.split(":", 2);
                if (credentialsArray.length > 1) {
                    String username = credentialsArray[0];
                    String password = credentialsArray[1];

                    if (username != null && !username.isBlank()) {
                        basic.setUsername(username);
                    }
                    if (password != null && !password.isBlank()) {
                        basic.setPassword(password);
                    }
                }
            }
        }
        return basic;
    }
}
