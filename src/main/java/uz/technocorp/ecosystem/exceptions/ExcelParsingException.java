package uz.technocorp.ecosystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 20.06.2025
 * @since v1.0
 */
@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExcelParsingException extends RuntimeException {
    private final int rowNumber;
    private final String details;

    public ExcelParsingException(String message, int rowNumber, String details, Throwable cause) {
        super(message, cause);
        this.rowNumber = rowNumber;
        this.details = details;
    }

    public ExcelParsingException(String message, int rowNumber, String details) {
        super(message);
        this.rowNumber = rowNumber;
        this.details = details;
    }
}
