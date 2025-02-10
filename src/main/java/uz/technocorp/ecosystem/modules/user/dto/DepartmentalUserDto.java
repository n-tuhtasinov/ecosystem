package uz.technocorp.ecosystem.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.01.2025
 * @since v1.0
 */
public record DepartmentalUserDto(
        @NotBlank(message = "Hodim FIOsi jo'natilmadi")
        String fullName,

        @NotNull(message = "Hodim JSHIRi jo'natilmadi")
        Long pin,

        @NotBlank(message = "Hodim roli tanlanmadi")
        String role,

        @NotEmpty(message = "Hodim bajaradigan ishlar tanlanmadi")
        List<@NotBlank(message = "Directionga bo'sh String qo'shish mumkin emas") String> directions,

        @NotNull(message = "Hodim bo'limi yoki departamenti tanlanmadi")
        Integer departmentId
) {
}
