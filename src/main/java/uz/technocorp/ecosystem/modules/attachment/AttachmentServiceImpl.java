package uz.technocorp.ecosystem.modules.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.utils.HtmlToPdfGenerator;

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
    private final HtmlToPdfGenerator htmlToPdfGenerator;

    @Override
    public String create(MultipartFile file, String folder) throws IOException {
        if (file != null) {
            // Create a directory for the file
            Path attachmentFilesPath = createDirectory(folder);

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
            return "/" + attachment.getPath();
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public String createPdfFromHtml(String htmlContent, String folder) {
        if (htmlContent != null && !htmlContent.isBlank()) {
            // Convert HTML to PDF
            byte[] pdfBytes = htmlToPdfGenerator.convertHtmlToPdf(htmlContent);

            // Create a directory for the PDF file
            Path directoryPath = createDirectory(folder);

            Path path = Paths.get(directoryPath + File.separator + System.currentTimeMillis() + ".pdf");
            try {
                Files.write(path, pdfBytes);
            } catch (IOException e) {
                log.error("PDF yaratishda xatolik {}", e.getMessage());
                throw new RuntimeException("PDF ni saqlashda xatolik yuz berdi");
            }
            Attachment attachment = repository.save(
                    new Attachment(
                            path.toString().replace("\\", "/"),
                            htmlContent));
            return "/" + attachment.getPath();
        }
        return null;
    }

    @Override
    public String getHtmlByPath(String path) {
        return repository.findByPath(path).map(
                        Attachment::getHtmlContent)
                .orElseThrow(() -> new ResourceNotFoundException("PDF file", "path", path));
    }

    @Override
    public void deleteByPath(String path) {
        repository.deleteByPath(path);
    }

    private Path createDirectory(String folder) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = now.getMonth().toString().toLowerCase();
        int day = now.getDayOfMonth();

        // Make a directory path
        Path path = Paths.get(String.format("files/%s/%s/%s/%s", folder, year, month, day));

        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("Error creating folder {}", e.getMessage());
            throw new RuntimeException("files/... papkani ochishda xatolik yuz berdi");
        }
        return path;
    }
}
