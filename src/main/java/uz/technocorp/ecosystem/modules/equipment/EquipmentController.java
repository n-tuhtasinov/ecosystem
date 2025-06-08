package uz.technocorp.ecosystem.modules.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;


//    @GetMapping
//    public ResponseEntity<?> getAll(@CurrentUser User user, @RequestParam Map<String, String> params) {
//        equipmentService.getAll(user, params);
//        return ResponseEntity.ok(new ApiResponse())
//    }


}
