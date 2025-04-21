package uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment.projection;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.04.2025
 * @since v1.0
 */
public interface HFRAssessmentView {

    Short getTin();
    Integer getSumScore();
    String getAddress();
    String getLegalName();
    String getHazardousFacilityName();
    String getRegionName();
    String getDistrictName();
    UUID getId();
}
