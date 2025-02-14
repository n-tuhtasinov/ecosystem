package uz.technocorp.ecosystem.modules.attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.configs.FileConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository repository;

    @Override
    public String create(MultipartFile file, String folder) throws IOException {
        if (file != null) {
            LocalDate localDate = LocalDate.now();
            Path path = FileConfig.path(folder, localDate.toString(), file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Attachment attachment = repository.save(
                    new Attachment(
                            path.toString().replace("\\", "/")
                    )
            );
            return attachment.getPath();
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
