package uz.technocorp.ecosystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.models.TokenResponse;
import uz.technocorp.ecosystem.modules.auth.AuthService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    AuthService authService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {


        Map<String, String> map = getJwtFromCookie(request);
        if (map == null || map.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (map.containsKey(AppConstants.ACCESS_TOKEN)){
            String accessToken = map.get(AppConstants.ACCESS_TOKEN);
            setAuthentication(request, accessToken);
        }else {
            if (map.containsKey(AppConstants.REFRESH_TOKEN)){
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
}
