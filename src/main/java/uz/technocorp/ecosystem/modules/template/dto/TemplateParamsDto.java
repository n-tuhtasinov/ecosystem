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
public class TemplateParamsDto {

    private String type;
    private Integer page;
    private Integer limit;

}
