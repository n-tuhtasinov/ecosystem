package uz.technocorp.ecosystem.modules.uploadexcel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.shared.ApiResponse;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 25.05.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/upload-excel")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/hf")
    public ResponseEntity<?> uploadExcel(@RequestParam MultipartFile file) {
        uploadService.uploadHf(file);
        return ResponseEntity.ok(new ApiResponse("Muvaffaqiyatli yuklandi"));
    }


}
