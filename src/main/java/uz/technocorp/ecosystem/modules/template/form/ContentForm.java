package uz.technocorp.ecosystem.modules.template.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 23.04.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentForm {

    private String content;

    // Other elements
    @Schema(hidden = true)
    private Integer id;
}
