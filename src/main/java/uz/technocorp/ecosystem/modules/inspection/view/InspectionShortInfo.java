package uz.technocorp.ecosystem.modules.inspection.view;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.06.2025
 * @since v1.0
 */
public interface InspectionShortInfo {

    UUID getId();

    Long getTin();

    String getLegalName();

    String getLegalAddress();

    LocalDate getStartDate();

    LocalDate getEndDate();

    String getSpecialCode();
}
