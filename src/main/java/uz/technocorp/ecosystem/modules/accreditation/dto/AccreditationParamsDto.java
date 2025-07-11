package uz.technocorp.ecosystem.modules.accreditation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Suxrob
 * @version 1.0
 * @created 11.07.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccreditationParamsDto {

    private String search;
    private Integer page = 1;
    private Integer size = 10;
}
