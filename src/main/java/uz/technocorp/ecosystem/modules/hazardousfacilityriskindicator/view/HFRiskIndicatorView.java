package uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.04.2025
 * @since v1.0
 */
public interface HFRiskIndicatorView {
    UUID getId();
    String getTin();
    String getIndicatorType();
    Integer getScore();
    String getDescription();
}
