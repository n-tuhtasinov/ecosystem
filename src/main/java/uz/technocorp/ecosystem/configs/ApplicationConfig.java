package uz.technocorp.ecosystem.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.technocorp.ecosystem.exceptions.UserNotFoundException;
import uz.technocorp.ecosystem.modules.user.UserRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 * @description all configs used throughout the project
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Bunday username topilmadi"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuditorAware<UUID> auditorAware (){
        return new AuditConfig();
    }

    @Bean
    public CorsConfigurationSource corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("https://b552-195-158-15-110.ngrok-free.app","http://localhost:5173", "https://ekotizim.technocorp.uz", "https://ekotizim.cirns.uz"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Ekotizim API").version("1.0.0"));
    }

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

//    @Bean
//    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
//        return builder.
//                featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
//                .modules(new JavaTimeModule())
//                .build();
//    }
}
