package uz.technocorp.ecosystem.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.technocorp.ecosystem.shared.ApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 * @description all custom exceptions which occur any time
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            if (errors.containsKey(field)) {
                String s = String.format("%s va %s", message, errors.get(field));
                errors.replace(field, s);
            } else {
                errors.put(field, message);
            }
        });
        logger.error(errors.toString());
        return ResponseEntity.badRequest().body(new ApiResponse("Kerakli qatorlar to'ldirilmadi",errors));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(ex.toString());
        return ResponseEntity.status(405).body(new ApiResponse("Bunday method mavjud emas"));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(ex.toString());
        return ResponseEntity.badRequest().body(new ApiResponse("Kerakli parametr ("+ex.getParameterName()+") yuborilmadi"));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(ex.toString());
        return ResponseEntity.badRequest().body(new ApiResponse("Qo'llab-quvvatlanmaydigan media turi ("+ex.getContentType()+") yuborildi"));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Yuborilgan JSONni java obyektga parse qilishda xatolik yuz berdi, bu asosan sana formati ('2024-05-18' formatida bo'lishi kerak) yoki enum qiymatlari bilan bog'liq bo'lishi mumkin: " + ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Login yoki parol xato!"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("Bunday amaliyot uchun sizga ruxsat berilmagan"));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object>handleDisabledException (DisabledException e){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("Siz aktiv holatda emassiz, mas'ul hodim bilan bog'laning"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataBaseErrorException(DataIntegrityViolationException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(String.format("Ma'lumotlar bazasiga saqlashda xatolik yuz berdi: \\n'%s'", e.getMostSpecificCause())));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(String.format("Bunday username topilmadi: '%s'", e.getMessage())));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error(e.getMessage());
        String parameterName = e.getName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(String.format("%s parametr uchun noto'g'ri qiymat berildi", parameterName)));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRunTimeException(RuntimeException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()!=null? e.getMessage(): "Runtime xatolik"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(String.format("Xatolik yuz berdi: \\n'%s'", e.getMessage())));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleException(CustomException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }








}
