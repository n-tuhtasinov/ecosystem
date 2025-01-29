package uz.technocorp.ecosystem.modules.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.security.CurrentUser;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final  UserService userService;

    @GetMapping("/me")
    ResponseEntity<?> getMe(@CurrentUser User user){
        if (user!=null){
            return ResponseEntity.ok(new ApiResponse(userService.getMe(user)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Siz avtorizatsiyadan o'tmagansiz"));
    }
}
