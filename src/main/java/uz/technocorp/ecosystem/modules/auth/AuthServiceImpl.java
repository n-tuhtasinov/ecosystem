package uz.technocorp.ecosystem.modules.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.models.TokenResponse;
import uz.technocorp.ecosystem.modules.auth.dto.AccessDataDto;
import uz.technocorp.ecosystem.modules.auth.dto.LoginDto;
import uz.technocorp.ecosystem.modules.auth.dto.OneIdDto;
import uz.technocorp.ecosystem.modules.auth.dto.UserInfoFromOneIdDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;
import uz.technocorp.ecosystem.security.JwtService;
import uz.technocorp.ecosystem.utils.ApiIntegrator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${app.one-id.client_id}")
    private String oneIdClientId;

    @Value("${app.one-id.client_secret}")
    private String oneIdClientSecret;

    @Value("${app.one-id.url}")
    private String oneIdUrl;

    @Override
    public UserMeDto login(LoginDto dto, HttpServletResponse response) {
        TokenResponse tokenResponse = generateToken(dto.username(), dto.password());
        setTokenToCookie(tokenResponse, response);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserMeDto(user.getId(), user.getFullName(), user.getPin(), user.getRole().name());
    }

    @Override
    public UserMeDto loginViaOneId(OneIdDto dto, HttpServletResponse response) {

        AccessDataDto accessData = getAccessData(dto);
        UserInfoFromOneIdDto userInfoFromOneIdDto = getUserInfoByAccessData(accessData);

        //check whether the user is legal, if yes it is not allowed
        if (userInfoFromOneIdDto.getAuth_method().name().equals("LEPKCSMETHOD")){
            throw new RuntimeException("Yuridik shaxslar tizimdan foydalana olmaydi.");
        }

        //find user by username
        Optional<User> optional = userRepository.findByUsername(userInfoFromOneIdDto.getPin());
        if (optional.isPresent()) {
            User user = optional.get();
            return getUserMeWithToken(user, accessData.getAccess_token(), response);
        }

        // generate password
        String password = generatePasswordFromOneIdToken(accessData.getAccess_token());

        // create user
//        User user = userRepository.save(
//                User.builder()
//                        .username(userInfoFromOneIdDto.getPin())
//                        .password(passwordEncoder.encode("root1234")) //TODO: keyinchalik default passwordni o'rniga tepadagi passwordni set qilish kerak
//                        .pin(Long.parseLong(userInfoFromOneIdDto.getPin()))
//                        .fullName(userInfoFromOneIdDto.getFull_name())
//                        .role(Role.CLIENT)
//                        .enabled(true)
//                        .build()
//        );
        return getUserMeWithToken(new User(), accessData.getAccess_token(), response);
    }

    private UserMeDto getUserMeWithToken(User user, String tokenFromOneId, HttpServletResponse response) {

        String password = generatePasswordFromOneIdToken(tokenFromOneId);
        String tempPassword = "root1234"; //TODO: keyinchalik bu tempPasswordni udalit qilish kerak

        // TODO: keyinchalik userga password ni set qilish uchun bu commentni ochib qo'yish kerak
//        user.setPassword(passwordEncoder.encode(password));
//        userRepository.save(user);

        // generate token
        TokenResponse tokenResponse = generateToken(user.getUsername(), tempPassword);

        // set generated token to cookie
        setTokenToCookie(tokenResponse, response);

        // return userMeDto
        return new UserMeDto(user.getId(), user.getFullName(), user.getPin(), user.getRole().name());
    }

    public void setTokenToCookie(TokenResponse tokenResponse, HttpServletResponse response) {
        if (tokenResponse.accessToken() == null || tokenResponse.refreshToken() == null) {
            throw new RuntimeException("Token yaratilmadi");
        }
        setCookie(response, tokenResponse.accessToken(), AppConstants.ACCESS_TOKEN, 60 * 60 * 24); // 24 hours
        setCookie(response, tokenResponse.refreshToken(), AppConstants.REFRESH_TOKEN, 60*60*24*7); // 7 days

    }

    @Override
    public void logout(HttpServletResponse response) {
        setCookie(response, "", AppConstants.ACCESS_TOKEN, 0 );
        setCookie(response, "", AppConstants.REFRESH_TOKEN, 0 );
    }


    public void setCookie(HttpServletResponse response, String tokenValue, String cookieName, long maxAgeInSeconds) {

        ResponseCookie cookie = ResponseCookie.from(cookieName, tokenValue)
                .path("/")
                .maxAge(maxAgeInSeconds)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private TokenResponse generateToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtService.generateToken(authentication, true);
        String refreshToken = jwtService.generateToken(authentication, false);
        return new TokenResponse(accessToken, refreshToken);

    }

    /**
     * generate a random range between 8 and 12 to substring from the last index
     * of access token String in order to set it as password of the user
     * @param tokenFromOneId accessToken taken from OneId
     * @return String for password
     */
    private String generatePasswordFromOneIdToken(String tokenFromOneId) {
        Random random = new Random();
        int randomRange = random.nextInt(4) + 8;
        return tokenFromOneId.substring(tokenFromOneId.length() - randomRange);
    }

    /**
     * to get full user info from OneId by access data which was taken earlier
     * @param dto Map in which there are access data
     * @return UserInfoDto
     */
    private UserInfoFromOneIdDto getUserInfoByAccessData(AccessDataDto dto) {
        Map<String, String> params= new HashMap<>();
        params.put("grant_type", "one_access_token_identify");
        params.put("client_id", oneIdClientId);
        params.put("client_secret", oneIdClientSecret);
        params.put("access_token", dto.getAccess_token());
        params.put("scope", dto.getScope());
        ApiIntegrator<UserInfoFromOneIdDto> apiIntegrator = new ApiIntegrator<>();
        return apiIntegrator.getData(UserInfoFromOneIdDto.class, params, oneIdUrl);
    }

    /**
     * to get access data from OneId by an one_authorization_code which was taken by frontend
     * @param dto OneIdDto
     * @return AccessDataDto included access data from OneId
     */
    private AccessDataDto getAccessData(OneIdDto dto) {
        Map<String, String> params= new HashMap<>();

        params.put("grant_type", "one_authorization_code");
        params.put("client_id", oneIdClientId); // oneIdClientId was given to us by OneID
        params.put("client_secret", oneIdClientSecret); // oneIdClientSecret was given to us by OneID
        params.put("code", dto.getCode());

        ApiIntegrator<AccessDataDto> apiIntegrator = new ApiIntegrator<>();
        return apiIntegrator.getData(AccessDataDto.class, params, oneIdUrl);
    }
}
