package uz.technocorp.ecosystem.modules.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.modules.auth.dto.LoginDto;
import uz.technocorp.ecosystem.modules.auth.dto.OneIdDto;
import uz.technocorp.ecosystem.modules.user.dto.UserMeDto;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDto dto, HttpServletResponse response){
        UserMeDto user = authService.login(dto, response);
        return ResponseEntity.ok(new ApiResponse(user));
    }

    @PostMapping(value = "/one-id")
    public HttpEntity<?> loginViaOneId(@Valid @RequestBody OneIdDto dto, HttpServletResponse response) {
        UserMeDto userMeDto = authService.loginViaOneId(dto, response);
        return ResponseEntity.ok(new ApiResponse(userMeDto));
    }

    @GetMapping(value = "/logout")
    public HttpEntity<?> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok(new ApiResponse("Siz tizimdan chiqdingiz"));
    }


}
