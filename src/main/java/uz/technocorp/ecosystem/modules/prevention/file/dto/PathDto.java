package uz.technocorp.ecosystem.modules.prevention.file.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Suxrob
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathDto {

    @NotBlank(message = "Fayl jo'natilmadi")
    private String path;
}
