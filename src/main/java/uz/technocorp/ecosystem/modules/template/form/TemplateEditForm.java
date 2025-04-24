package uz.technocorp.ecosystem.modules.template.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 15.04.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateEditForm {

    @NotBlank(message = "Template nomi jo'natilmadi")
    private String name;

    private String description;

    // Other elements
    @Schema(hidden = true)
    private Integer id;
}
