package uz.technocorp.ecosystem.modules.hfriskindicator.view;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.04.2025
 * @since v1.0
 */
public interface RiskIndicatorView {
    UUID getId();
    String getTin();
    String getIndicatorType();
    Integer getScore();
    String getDescription();
    String getFilePath();
    LocalDate getFileDate();
    Integer getScoreValue();
    LocalDate getCancelledDate();
    Integer getIntervalId();
}
