package uz.technocorp.ecosystem.modules.attachment;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
public interface AttachmentService {
    String create(MultipartFile file, String folder) throws IOException;
    void delete(UUID id);
}
