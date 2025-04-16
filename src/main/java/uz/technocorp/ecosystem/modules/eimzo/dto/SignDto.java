package uz.technocorp.ecosystem.modules.eimzo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignDto {

    @NotBlank(message = "Sign jo'natilmadi")
    private String sign;
}
