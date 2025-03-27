package uz.technocorp.ecosystem.modules.irsappeal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.technocorp.ecosystem.models.ApiResponse;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.models.ResponseMessage;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 26.03.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/irs-appeals")
@RequiredArgsConstructor
public class IrsAppealController {

    private final IrsAppealService irsAppealService;

    @PostMapping
    public ResponseEntity<?> create(@CurrentUser User user, @Valid @RequestBody IrsDto irsDto) {
        irsAppealService.create(user, irsDto);
        return ResponseEntity.ok().body(new ApiResponse(ResponseMessage.CREATED));
    }
}
