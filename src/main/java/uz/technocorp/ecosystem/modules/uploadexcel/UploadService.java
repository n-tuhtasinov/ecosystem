package uz.technocorp.ecosystem.modules.uploadexcel;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 25.05.2025
 * @since v1.0
 */
public interface UploadService {
    void uploadHf(MultipartFile file);
}
