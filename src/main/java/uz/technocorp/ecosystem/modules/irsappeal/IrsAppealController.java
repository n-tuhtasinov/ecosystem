//package uz.technocorp.ecosystem.modules.irsappeal;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import uz.technocorp.ecosystem.models.ApiResponse;
//import uz.technocorp.ecosystem.models.AppConstants;
//import uz.technocorp.ecosystem.models.ResponseMessage;
//import uz.technocorp.ecosystem.modules.appeal.Appeal;
//import uz.technocorp.ecosystem.modules.appeal.dto.IrsDto;
//import uz.technocorp.ecosystem.modules.user.User;
//import uz.technocorp.ecosystem.security.CurrentUser;
//
//import java.util.UUID;
//
///**
// * @author Nurmuhammad Tuhtasinov
// * @version 1.0
// * @created 26.03.2025
// * @since v1.0
// */
//@RestController
//@RequestMapping("/api/v1/irs-appeals")
//@RequiredArgsConstructor
//public class IrsAppealController {
//
//    private final IrsAppealService irsAppealService;
//
//    @PostMapping
//    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody IrsDto irsDto) {
//        irsAppealService.create(user, irsDto);
//        return ResponseEntity.ok().body(new ApiResponse(ResponseMessage.CREATED));
//    }
//
//    @GetMapping("/{appealId}")
//    public ResponseEntity<?> getByAppealId(@PathVariable UUID appealId) {
//        IrsAppeal byId = irsAppealService.getByAppealId(appealId);
//        return ResponseEntity.ok().body(new ApiResponse(byId));
//    }
//
//
//}
