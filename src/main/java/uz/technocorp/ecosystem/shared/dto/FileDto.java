package uz.technocorp.ecosystem.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Suxrob
 * @version 1.0
 * @created 28.08.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto implements Serializable {

    private String path;
    private String number;
    private LocalDate uploadDate;
    private LocalDate expiryDate;

    public FileDto(String path, LocalDate expiryDate) {
        this.path = path;
        this.expiryDate = expiryDate;
    }
}
