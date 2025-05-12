package uz.technocorp.ecosystem.modules.attachment;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
public interface AttachmentService {
    String create(MultipartFile file, String folder);

    String createPdfFromHtml(String htmlContent, String folder);

    String getHtmlByPath(String path);

    void deleteByPath(String path);
}
