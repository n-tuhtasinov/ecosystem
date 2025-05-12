package uz.technocorp.ecosystem.modules.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.utils.Generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Objects;

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

    @Value("${app.file-base.url}")
    private String fileBaseUrl;

    private final AttachmentRepository repository;
    private final Generator generator;

    @Override
    public String create(MultipartFile file, String folder) {
        if (file == null) {
            return null;
        }
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
        return saveToDatabase(path, "");
    }

    @Override
    public String createPdfFromHtml(String htmlContent, String folder) {
        if (htmlContent == null || htmlContent.isBlank()) {
            return null;
        }
        // Create a directory and path for the PDF file
        Path directoryPath = createDirectory(folder);
        String fileName = System.currentTimeMillis() + ".pdf";
        Path filePath = directoryPath.resolve(fileName);

        // Generate QR code and add to HTML
        String htmlWithQrCode = addQrCodeToHtml(htmlContent, filePath);

        // Generate PDF
        byte[] pdfBytes = generator.convertHtmlToPdf(htmlWithQrCode);
        try {
            // Save to DB and return the file path
            Files.write(filePath, pdfBytes);

            // Saving to the database and returning a standardized path
            return saveToDatabase(filePath, htmlWithQrCode);
        } catch (IOException e) {
            log.error("Error saving PDF file: {}", e.getMessage());
            throw new RuntimeException("Error saving PDF file", e);
        }
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

    private String saveToDatabase(Path filePath, String htmlContent) {
        String standardizedPath = getStandardizedPath(filePath);
        Attachment savedAttachment = repository.save(new Attachment(standardizedPath, htmlContent));
        return "/" + savedAttachment.getPath();
    }

    private String addQrCodeToHtml(String htmlContent, Path filePath) {
        String fileUrl = fileBaseUrl + getStandardizedPath(filePath);
        String qrCodeBase64 = generator.generateQRCodeBase64(fileUrl, 250, 250);
        return htmlContent +
                "<div style=\"text-align: center;\">" +
                "<img src=\"data:image/png;base64," + qrCodeBase64 + "\" " +
                "style=\"width: 150px; display: block; margin: 0 auto;\"/>" +
                "</div>";
    }

    private String getStandardizedPath(Path path) {
        return path.toString().replace("\\", "/");
    }
}
