package uz.technocorp.ecosystem.modules.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public record DepartmentDto(
        @NotBlank(message = "Departament yoki bo'lim nomi kiritilmadi")
        String name,

        @NotNull(message = "Departament yoki bo'lim klassifikator raqami kiritilmadi")
        Integer classifier
) {
}
