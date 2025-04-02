package uz.technocorp.ecosystem.modules.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.modules.user.dto.ChairmanUserDto;
import uz.technocorp.ecosystem.modules.user.dto.CommitteeUserDto;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;
import uz.technocorp.ecosystem.modules.user.dto.OfficeUserDto;
import uz.technocorp.ecosystem.security.CurrentUser;

import java.util.Map;
import java.util.UUID;

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

    @PostMapping("/committee-users")
    ResponseEntity<?> createCommitteeUser(@Valid @RequestBody CommitteeUserDto dto){
        userService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Qo'mita hodimi muvaffaqiyatli qo'shildi"));
    }

    @PostMapping("/chairman-user")
    ResponseEntity<?> createChairmanUser(@Valid @RequestBody ChairmanUserDto dto){
        userService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Qo'mita raisi muvaffaqiyatli qo'shildi"));
    }

    @PostMapping("/office-users")
    ResponseEntity<?> createOfficeUser(@Valid @RequestBody OfficeUserDto dto){
        userService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Hududiy boshqarma hodimi muvaffaqiyatli qo'shildi"));
    }

    @PutMapping("/committee-users/{userId}")
    ResponseEntity<?> updateCommitteeUser(@PathVariable UUID userId, @Valid @RequestBody CommitteeUserDto dto){
        userService.update(userId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Qo'mita hodimi muvaffaqiyatli o'zgartirildi"));
    }

    @PutMapping("/chairman-user/{userId}")
    ResponseEntity<?> updateChairmanUser(@PathVariable UUID userId, @Valid @RequestBody ChairmanUserDto dto){
        userService.update(userId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Qo'mita raisi muvaffaqiyatli o'zgartirildi"));
    }

    @PutMapping("/office-users/{userId}")
    ResponseEntity<?> updateOfficeUser(@PathVariable UUID userId, @Valid @RequestBody OfficeUserDto dto){
        userService.update(userId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Hududiy boshqarma hodimi muvaffaqiyatli o'zgartirildi"));
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Foydalanuvchi muvaffaqiyatli o'chirildi"));
    }

    @PatchMapping("/{userId}")
    ResponseEntity<?> changeUserEnabled(@PathVariable UUID userId, @RequestParam Boolean enabled) {
        userService.changeUserEnabled(userId, enabled);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Foydalanuvchi holati muvaffaqiyatli o'zgartirildi"));
    }

    @PutMapping("/{userId}")
    ResponseEntity<?> updateLegalUser(@PathVariable UUID userId, @RequestBody LegalUserDto dto) {
        userService.updateLegalUser(userId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Foydalanuvchi holati muvaffaqiyatli o'zgartirildi"));
    }

    @GetMapping("/committee-users")
    ResponseEntity<?> getCommitteeUsers(@RequestParam Map<String, String> params) {
//        userService.getCommitteeUsers(params);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Qo'mita hodimi muvaffaqiyatli qo'shildi"));
    }



}
