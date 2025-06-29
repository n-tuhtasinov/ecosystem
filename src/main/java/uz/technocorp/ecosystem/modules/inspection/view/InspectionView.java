package uz.technocorp.ecosystem.modules.inspection.view;

import uz.technocorp.ecosystem.modules.user.dto.InspectorDto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.05.2025
 * @since v1.0
 */
public interface InspectionView {

    UUID getId();

    Long getTin();

    LocalDate getStartDate();

    LocalDate getEndDate();

    String getStatus();

    String getSpecialCode();

    String getSchedulePath();

    String getNotificationLetterPath();

    LocalDate getNotificationLetterDate();

    String getOrderPath();

    String getDecreePath();

    String getDecreeNumber();

    LocalDate getDecreeDate();

    String getProgramPath();

    String getMeasuresPath();

    String getResultPath();

    String getInspectors();
}
