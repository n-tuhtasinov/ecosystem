package uz.technocorp.ecosystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @since 1.0
 * @created 29.01.2025
 * @description throw exception when it is not possible to find the item from DataBase, because of its deletion or not creation
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends NoSuchElementException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s ushbu %s : '%s' bo'yicha topilmadi!", resourceName, fieldName, fieldValue));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
