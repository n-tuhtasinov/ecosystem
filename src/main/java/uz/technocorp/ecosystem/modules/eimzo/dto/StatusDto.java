package uz.technocorp.ecosystem.modules.eimzo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 14.04.2025
 * @since v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {

    private String status;
    private String dateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ip;

    public StatusDto(String status, String dateTime) {
        this.status = status;
        this.dateTime = dateTime;
    }

}
