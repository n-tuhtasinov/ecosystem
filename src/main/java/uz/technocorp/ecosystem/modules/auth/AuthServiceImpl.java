package uz.technocorp.ecosystem.modules.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.integration.iip.IIPService;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisIntervalRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.shared.TokenResponse;
import uz.technocorp.ecosystem.modules.auth.dto.AccessDataDto;
import uz.technocorp.ecosystem.modules.auth.dto.LoginDto;
import uz.technocorp.ecosystem.modules.auth.dto.OneIdDto;
import uz.technocorp.ecosystem.modules.auth.dto.UserInfoFromOneIdDto;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.dto.IndividualUserDto;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;
import uz.technocorp.ecosystem.security.JwtService;
import uz.technocorp.ecosystem.utils.ApiIntegrator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final DistrictRepository districtRepository;
    private final PasswordEncoder passwordEncoder;
    private final IIPService iipService;
    private final ProfileService profileService;
    private final RiskAnalysisIntervalRepository intervalRepository;

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
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository.findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Interval ma'lomoti topilmadi"));
        return new UserMeDto(user.getId(), user.getName(), user.getRole().name(), user.getDirections(), riskAnalysisInterval);
    }

    @Override
    @Transactional
    public UserMeDto loginViaOneId(OneIdDto dto, HttpServletResponse response) {

        AccessDataDto accessData = getAccessData(dto);
        log.error("Token olindi: {}", accessData.toString());
        UserInfoFromOneIdDto userInfoFromOneIdDto = getUserInfoByAccessData(accessData);
        log.error("User ma'lumotlari olindi: {}", userInfoFromOneIdDto.toString());

        //check whether the user is legal or not
        if (userInfoFromOneIdDto.getAuth_method().name().equals("LEPKCSMETHOD")){
            String legalTin = userInfoFromOneIdDto.getPkcs_legal_tin();

            //find user by username, if there is not, should create a new one
            Optional<User> optional = userRepository.findByUsername(legalTin);
            if (optional.isPresent()){
                User user = optional.get();
                return getUserMeWithToken(user, accessData.getAccess_token(), response);
            }

            //create a new legal user. The legal user has only "appeal" in the direction list when he is first created
            //get gnk legal info from IIP
            LegalUserDto legalInfo = iipService.getGnkInfo(legalTin);

            User user = userService.create(legalInfo);
            return getUserMeWithToken(user, accessData.getAccess_token(), response);
        }

        //find user by username, if there is not, should create a new one
        String pin = userInfoFromOneIdDto.getPin();
        Optional<User> optional = userRepository.findByUsername(pin);
        if (optional.isPresent()){
            profileService.addPhoneNumber(optional.get().getProfileId(), userInfoFromOneIdDto.getMob_phone_no()); // update the profile with phoneNumber
            return getUserMeWithToken(optional.get(), accessData.getAccess_token(), response);
        }

        throw new RuntimeException("Tizimda tashqi jismoniy shaxslar uchun hali ruxsat berilmagan"); //TODO keyinchalik exceptionni olib tashlash kerak
        //create a new individual user
//        User user = userService.create(new IndividualUserDto(userInfoFromOneIdDto.getFull_name(), Long.valueOf(userInfoFromOneIdDto.getPin()), userInfoFromOneIdDto.getMob_phone_no()));
//        return getUserMeWithToken(user, accessData.getAccess_token(), response);
    }

    private UserMeDto getUserMeWithToken(User user, String tokenFromOneId, HttpServletResponse response) {

        String password = generatePasswordFromOneIdToken(tokenFromOneId);
        password = "root1234"; //TODO: keyinchalik bu tempPasswordni udalit qilish kerak

        // TODO: keyinchalik userga password ni set qilish uchun bu commentni ochib qo'yish kerak
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        // generate token
        TokenResponse tokenResponse = generateToken(user.getUsername(), password);

        // set generated token to cookie
        setTokenToCookie(tokenResponse, response);

        RiskAnalysisInterval riskAnalysisInterval = intervalRepository.findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Interval ma'lomoti topilmadi"));

        // return userMeDto
        return new UserMeDto(user.getId(), user.getName(), user.getRole().name(), user.getDirections(), riskAnalysisInterval);
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
        params.put("code", dto.code());

        ApiIntegrator<AccessDataDto> apiIntegrator = new ApiIntegrator<>();
        return apiIntegrator.getData(AccessDataDto.class, params, oneIdUrl);
    }
}
