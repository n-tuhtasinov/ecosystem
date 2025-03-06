package uz.technocorp.ecosystem.modules.appeal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.03.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealSearchCriteria {

    private String status;

    private String appealType;

    private String date;

    private String legalTin;

    private Integer officeId;
}
