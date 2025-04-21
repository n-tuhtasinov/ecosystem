package uz.technocorp.ecosystem.modules.template.dto;

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
public class TemplateDto {

    private Integer id;
    private String name;
    private String description;
    private Integer ord;
    private String type;
    private String content;

}
