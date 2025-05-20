package uz.technocorp.ecosystem.modules.prevention.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreventionParamsDto {

    private Boolean isPassed;
    private String search;
    private Integer page;
    private Integer size;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean viewed;
    private Integer officeId;
    private UUID inspectorId;
}
