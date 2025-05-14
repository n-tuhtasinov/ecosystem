package uz.technocorp.ecosystem.modules.prevention.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 13.05.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreventionView {

    private String id;
    private String type;
    private String content;
    private Integer year;
    private boolean viewed;
    private LocalDateTime viewDate;
    private LocalDateTime createdAt;
    private String createdBy;
    private String inspectorName;
    private Long profileTin;
    private String profileName;
    private String profileAddress;
}
