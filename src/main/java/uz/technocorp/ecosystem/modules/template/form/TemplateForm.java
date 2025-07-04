package uz.technocorp.ecosystem.modules.template.form;

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
public class TemplateForm {

    @NotBlank(message = "Template nomi jo'natilmadi")
    private String name;

    @NotBlank(message = "Shablon turi tanlanmadi")
    private String type;

    private String content;

    private String description;
}
