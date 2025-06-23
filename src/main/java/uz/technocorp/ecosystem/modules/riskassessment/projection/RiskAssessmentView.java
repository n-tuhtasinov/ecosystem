package uz.technocorp.ecosystem.modules.riskassessment.projection;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.04.2025
 * @since v1.0
 */
public interface RiskAssessmentView {

    Integer getScore();
    String getAddress();
    String getName();
    String getRegionName();
    String getDistrictName();
    UUID getId();
    String getRegistryNumber();
}
