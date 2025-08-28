package uz.technocorp.ecosystem.modules.uploadexcel.irs;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.08.2025
 * @since v1.0
 */
public interface UploadIrsService {
    void upload(MultipartFile file);
}
