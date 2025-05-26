package uz.technocorp.ecosystem.modules.appeal.view;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 13.05.2025
 * @since v1.0
 */
public interface AppealViewByPeriod {
    UUID getId();
    String getAppealType();
    String getNumber();
    String getLegalName();
    Long getLegalTin();
    String getAddress();
    String getPhoneNumber();
    LocalDate getDeadline();
}
