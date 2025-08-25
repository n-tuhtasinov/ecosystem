package uz.technocorp.ecosystem.modules.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.utils.Generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public String createPdfFromHtml(String htmlContent, String folder, Map<String, String> parameters, Boolean saveToDb) {
        if (htmlContent == null || htmlContent.isBlank()) {
            return null;
        }
        // Create a directory and path for the PDF file
        Path directoryPath = createDirectory(folder);
        String fileName = System.currentTimeMillis() + ".pdf";
        Path filePath = directoryPath.resolve(fileName);

        // Create qrCodeBase64 and set to parameters
        parameters.put("qrCodeBase64", getQrCodeBase64(filePath));

        // Replace variables
        String content = replaceVariables(htmlContent, parameters);

        // Generate PDF
        byte[] pdfBytes = generator.convertHtmlToPdf(content);
        try {
            // Write file to folder
            Files.write(filePath, pdfBytes);

            return saveToDb
                    ? saveToDatabase(filePath, content) // save to database and return path
                    : getStandardizedPath(filePath); // just return path
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

    @Override
    public void deleteByPaths(Collection<String> pathList) {
        if (pathList == null || pathList.isEmpty()) {
            throw new RuntimeException("Files map bo'sh bo'lishi mumkin emas!");
        }
        List<String> validPaths = pathList.stream()
                .filter(Objects::nonNull)
                .filter(path -> !path.trim().isEmpty())
                .collect(Collectors.toList());

        if (!validPaths.isEmpty()) {
            repository.deleteByPaths(validPaths);
        }
    }

    @Override
    public void deleteFileFromStorage(String filePath) {

        if (filePath == null || filePath.isBlank() || !filePath.startsWith("/files")) {
            throw new RuntimeException("Tizimda faqat /files deb boshlangan fayllarnigina o'chirish mumkin");
        }

        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }

        try {
            Path fileToDelete = Paths.get(filePath);
            boolean deleted = Files.deleteIfExists(fileToDelete);
            if (deleted) {
                log.info("Fayl muvaffaqiyatli o'chirildi: {}", fileToDelete.toAbsolutePath());
            } else {
                log.warn("O'chirilishi kerak bo'lgan fayl topilmadi: {}", fileToDelete.toAbsolutePath());
                throw new RuntimeException("O'chirilishi kerak bo'lgan fayl topilmadi: " + filePath);
            }
        } catch (Exception e) {
            log.error("Faylni o'chirib bo'lmadi: {}. Xatolik: {}", filePath, e.getMessage());
            throw new RuntimeException(String.format("Faylni o'chirib bo'lmadi: '%s', Xatolik: '%s'", filePath, e.getMessage()));
        }
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
        return savedAttachment.getPath();
    }

    private String replaceVariables(String htmlContent, Map<String, String> variables) {
        String result = htmlContent;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            if (entry.getValue() == null) {
                throw new CustomException(entry.getKey() + " is null");
            }
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }

    private String getStandardizedPath(Path path) {
        return "/" + path.toString().replace("\\", "/");
    }

    private String getQrCodeBase64(Path filePath) {
        return generator.generateQRCodeBase64(fileBaseUrl + getStandardizedPath(filePath), 250, 250);
    }
}
