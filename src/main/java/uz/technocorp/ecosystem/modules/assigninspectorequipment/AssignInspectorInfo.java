package uz.technocorp.ecosystem.modules.assigninspectorequipment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.06.2025
 * @since v1.0
 */
public interface AssignInspectorInfo {
    UUID getId();
    String getInspectorName();
    LocalDateTime getDate();
    Integer getIntervalId();
    LocalDate getStartDate();
    LocalDate getEndDate();
}
