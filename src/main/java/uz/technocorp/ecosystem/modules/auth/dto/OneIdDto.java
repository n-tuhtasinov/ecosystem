package uz.technocorp.ecosystem.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneIdDto {

    @NotBlank(message = "OneId tomonidan olingan kod jo'natilmadi")
    private String code;
}
