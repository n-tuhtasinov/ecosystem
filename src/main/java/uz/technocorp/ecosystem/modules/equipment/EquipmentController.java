package uz.technocorp.ecosystem.modules.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
