package uz.technocorp.ecosystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private Map<String, String> errors;
    private Object data;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ApiResponse(Object data) {
        this.data = data;
        this.message = "Success";
    }

}
