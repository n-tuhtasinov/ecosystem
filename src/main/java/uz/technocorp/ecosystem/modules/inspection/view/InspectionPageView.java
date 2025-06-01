package uz.technocorp.ecosystem.modules.inspection.view;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.05.2025
 * @since v1.0
 */
public interface InspectionPageView {

    UUID getId();
    Long getTin();
    String getRegionName();
    String getDistrictName();
    String getLegalName();
    String getLegalAddress();

}
