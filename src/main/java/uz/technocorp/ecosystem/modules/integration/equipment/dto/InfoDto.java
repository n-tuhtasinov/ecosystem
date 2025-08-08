package uz.technocorp.ecosystem.modules.integration.equipment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Suxrob
 * @version 1.0
 * @created 21.07.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoDto<T> {

    private Long tinOrPin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String legalName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String legalAddress;
    private String fullName;
    private String regionName;
    private String districtName;
    private String phoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> equipment;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> hf;
}
