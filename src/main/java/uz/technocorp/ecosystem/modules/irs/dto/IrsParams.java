package uz.technocorp.ecosystem.modules.irs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 09.06.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IrsParams {
    private Integer page;
    private Integer size;
    private Long legalTin;
    private String registryNumber;
    private Integer regionId;
    private Integer districtId;
    private LocalDate startDate;
    private LocalDate endDate;
}
