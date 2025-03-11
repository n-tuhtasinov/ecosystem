package uz.technocorp.ecosystem.modules.department.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public record DepartmentDto(
        @NotBlank(message = "Departament yoki bo'lim nomi kiritilmadi")
        String name
) {
}
