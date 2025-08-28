package uz.technocorp.ecosystem.modules.uploadexcel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.uploadexcel.equipment.UploadEquipmentExcelService;
import uz.technocorp.ecosystem.modules.uploadexcel.hf.UploadHfExcelService;
import uz.technocorp.ecosystem.modules.uploadexcel.irs.UploadIrsService;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.util.Map;

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
    private final UploadHfExcelService uploadHfService;
    private final Map<String, UploadEquipmentExcelService> uploadEquipmentServices;
    private final UploadIrsService uploadIrsService;

    @PostMapping("/hf")
    public ResponseEntity<?> uploadHf(@RequestParam("file") MultipartFile file) {
        uploadHfService.upload(file);
        return ResponseEntity.ok(new ApiResponse("Fayl muvaffaqiyatli yuklandi"));
    }

    @PostMapping("/equipment/{equipmentType}")
    public ResponseEntity<?> uploadEquipment(@PathVariable EquipmentType equipmentType, @RequestParam MultipartFile file) {
        UploadEquipmentExcelService equipmentExcelService = uploadEquipmentServices.get(equipmentType.name());
        equipmentExcelService.upload(file);
        return ResponseEntity.ok(new ApiResponse("Fayl muvaffaqiyatli yuklandi"));
    }

    @PostMapping("/irs")
    public ResponseEntity<?> uploadIrs(@RequestParam("file") MultipartFile file) {
        uploadIrsService.upload(file);
        return ResponseEntity.ok(new ApiResponse("Fayl muvaffaqiyatli yuklandi"));
    }
}
