package uz.technocorp.ecosystem.modules.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository repository;

    @Override
    public String create(MultipartFile file, String folder) throws IOException {
        if (file != null) {
            LocalDate now = LocalDate.now();
            int year = now.getYear();
            String month = now.getMonth().toString().toLowerCase();
            int day = now.getDayOfMonth();

            //create folder
            Path attachmentFilesPath = Paths
                    .get(String.format("files/%s/%s/%s/%s", folder, year, month, day));

            try {
                Files.createDirectories(attachmentFilesPath);
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException("files/... papkani ochishda xatolik yuz berdi");
            }

            String originalFilename = file.getOriginalFilename();
            int index = Objects.requireNonNull(originalFilename).lastIndexOf(".");
            String extension = Objects.requireNonNull(originalFilename).substring(index);
            long randomName = System.currentTimeMillis();

            Path path = Paths.get(attachmentFilesPath + File.separator + randomName + extension);
            try {
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException("Faylni papkaga saqlashda xatolik yuz berdi");
            }

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
