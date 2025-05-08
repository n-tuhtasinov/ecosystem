package uz.technocorp.ecosystem.configs;


import org.springframework.context.annotation.Configuration;
import uz.technocorp.ecosystem.shared.ResponseMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileConfig {

//    private final static String parent = new File(System.getProperty("user.dir")).getParent();

    public static Path path(String mainFolder, String subFolder, String fileName) throws IOException {
        Path firstPath = Paths.get("files");
        Path secondPath = Paths.get(firstPath.toString().concat(File.separator + mainFolder));
        Path lastPath = Paths.get(secondPath.toString().concat(File.separator + subFolder));
        try {
            Files.createDirectories(firstPath);
            Files.createDirectories(secondPath);
            Files.createDirectories(lastPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(ResponseMessage.DIRECTORY_NOT_CREATED);
        }
        return Paths.get(lastPath + File.separator + System.currentTimeMillis() + fileName);
    }
}
