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
 * @created 20.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/upload-excel")
@RequiredArgsConstructor
public class UploadExcelController {

    private final UploadHfExcelService hfExcelService;

    @PostMapping("/hf")
    public ResponseEntity<?> uploadHf(@RequestParam("file") MultipartFile file) {
        hfExcelService.upload(file);
        return ResponseEntity.ok(new ApiResponse("Fayl muvaffaqiyatli yuklandi"));
    }
}
