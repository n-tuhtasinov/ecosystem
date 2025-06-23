package uz.technocorp.ecosystem.modules.uploadexcel.hf;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 21.06.2025
 * @since v1.0
 */
public interface UploadHfExcelService {
    void upload(MultipartFile file);
}
