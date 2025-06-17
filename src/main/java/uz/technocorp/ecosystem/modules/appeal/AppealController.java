package uz.technocorp.ecosystem.modules.appeal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.appeal.dto.*;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewByPeriod;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByReply;
import uz.technocorp.ecosystem.modules.document.view.DocumentViewByRequest;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.security.CurrentUser;
import uz.technocorp.ecosystem.shared.ApiResponse;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/appeals")
@RequiredArgsConstructor
public class AppealController {

    private final AppealService service;
    private final DocumentService documentService;

    @PostMapping("/set-inspector")
    public ResponseEntity<?> setInspector(@Valid @RequestBody SetInspectorDto dto) {
        service.setInspector(dto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.COMPLETED));
    }

    @GetMapping
    public ResponseEntity<?> getAllAppeals(@CurrentUser User user, @RequestParam Map<String, String> params) {
        Page<AppealCustom> appeals = service.getAppealCustoms(user, params);
        return ResponseEntity.ok(new ApiResponse(appeals));
    }

    // @PreAuthorize("hasRole('INSPECTOR')")
    @GetMapping("/period")
    public ResponseEntity<?> getAllByPeriodAndInspector(@CurrentUser User user, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<AppealViewByPeriod> list = service.getAllByPeriodAndInspector(user, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse(list));
    }

    @GetMapping("/{appealId}")
    public ResponseEntity<?> getById(@PathVariable UUID appealId) {
        AppealViewById byId = service.getById(appealId);
        return ResponseEntity.ok(new ApiResponse(byId));
    }

    @PostMapping("/reply/generate-pdf")
    public ResponseEntity<ApiResponse> generatePdfFromForm(@CurrentUser User user, @Valid @RequestBody ReplyDto replyDto) {
        String path = service.prepareReplyPdfWithParam(user, replyDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/reply")
    public ResponseEntity<ApiResponse> reply(@CurrentUser User user, @Valid @RequestBody SignedReplyDto replyDto, HttpServletRequest request) {
        service.saveReplyAndSign(user, replyDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @PostMapping("/reply/reject/generate-pdf")
    public ResponseEntity<ApiResponse> generateRejectPdf(@CurrentUser User user, @Valid @RequestBody ReplyDto replyDto) {
        String path = service.prepareRejectPdfWithParam(user, replyDto);
        return ResponseEntity.ok(new ApiResponse("PDF fayl yaratildi", path));
    }

    @PostMapping("/reply/reject")
    public ResponseEntity<ApiResponse> replyReject(@CurrentUser User user, @Valid @RequestBody SignedReplyDto replyDto, HttpServletRequest request) {
        service.replyReject(user, replyDto, request);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED));
    }

    @GetMapping("/{appealId}/request-docs")
    public ResponseEntity<?> getRequestDocsById(@PathVariable UUID appealId) {
        List<DocumentViewByRequest> list = documentService.getRequestDocumentsByAppealId(appealId);
        return ResponseEntity.ok(new ApiResponse(list));
    }

    @GetMapping("/{appealId}/reply-docs")
    public ResponseEntity<?> getReplyDocsById(@CurrentUser User user, @PathVariable UUID appealId) {
        List<DocumentViewByReply> list = documentService.getReplyDocumentsByAppealId(user, appealId);
        return ResponseEntity.ok(new ApiResponse(list));
    }

    @PostMapping("/rejection")
    public ResponseEntity<?> reject(@CurrentUser User user, @Valid @RequestBody RejectDto rejectDto) {
        service.reject(user, rejectDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.REJECTED));
    }

    @PostMapping("/confirmation")
    public ResponseEntity<?> confirm(@CurrentUser User user, @Valid @RequestBody ConfirmationDto confirmationDto) {
        service.confirm(user, confirmationDto);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CONFIRMED));
    }
}
