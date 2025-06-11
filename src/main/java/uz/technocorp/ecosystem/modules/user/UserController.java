package uz.technocorp.ecosystem.modules.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.user.dto.CommitteeUserDto;
import uz.technocorp.ecosystem.modules.user.dto.OfficeUserDto;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.modules.user.view.CommitteeUserView;
import uz.technocorp.ecosystem.modules.user.view.OfficeUserView;
import uz.technocorp.ecosystem.modules.user.view.UserViewById;
import uz.technocorp.ecosystem.modules.user.view.UserViewByLegal;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;

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
        if (!dto.getRole().equals(Role.CHAIRMAN.name())){
            if (dto.getDepartmentId()==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Departament yoki bo'lim jo'natilmadi"));
            }
        }
        userService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Qo'mita hodimi muvaffaqiyatli qo'shildi"));
    }

    @PostMapping("/office-users")
    ResponseEntity<?> createOfficeUser(@Valid @RequestBody OfficeUserDto dto){
        userService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Hududiy boshqarma hodimi muvaffaqiyatli qo'shildi"));
    }

    @PutMapping("/committee-users/{userId}")
    ResponseEntity<?> updateCommitteeUser(@PathVariable UUID userId, @Valid @RequestBody CommitteeUserDto dto){
        if (!dto.getRole().equals(Role.CHAIRMAN.name())){
            if (dto.getDepartmentId()==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Departament yoki bo'lim jo'natilmadi"));
            }
        }
        userService.update(userId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Qo'mita hodimi muvaffaqiyatli o'zgartirildi"));
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

    @PutMapping("/legal/{tin}")
    ResponseEntity<?> updateLegalUser(@PathVariable Long tin) {
        userService.updateLegalUser(tin);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Foydalanuvchi ma'lumotlari muvaffaqiyatli o'zgartirildi"));
    }

    @GetMapping("/committee-users")
    ResponseEntity<?> getCommitteeUsers(@RequestParam(required = false) Map<String, String> params) {
        Page<CommitteeUserView> page = userService.getCommitteeUsers(params);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(page));
    }

    @GetMapping("/office-users")
    ResponseEntity<?> getOfficeUsers(@RequestParam(required = false) Map<String, String> params) {
        Page<OfficeUserView> page = userService.getOfficeUsers(params);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(page));
    }

    @Operation(
            summary = "Inspector listini olish",
            parameters = {
                    @Parameter(name = "officeId", description = "Hududiy bo'lim ID si", schema = @Schema(type = "UUID")),
                    @Parameter(name = "inspectorName", description = "Inspector ismi bo'yicha filter", schema = @Schema(type = "string"))
            }
    )
    @GetMapping("/office-users/inspectors/select")
    ResponseEntity<ApiResponse> getInspectors(@CurrentUser User user, @RequestParam(required = false) Map<String, String> params) {
        return ResponseEntity.ok(new ApiResponse(userService.getInspectors(user, params)));
    }

    @GetMapping("/{userId}")
    ResponseEntity<?> getUserById(@PathVariable UUID userId) {
        UserViewById byId = userService.getById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(byId));
    }

    @GetMapping("/legal/{tin}")
    ResponseEntity<?> getLegalUser(@PathVariable Long tin) {
        UserViewByLegal user = userService.getLegalUserByTin(tin);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(user));
    }



}
