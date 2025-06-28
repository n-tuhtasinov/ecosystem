package uz.technocorp.ecosystem.modules.inspection.view;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 27.06.2025
 * @since v1.0
 */
public interface InspectorInfoForInspectionAct {

    UUID getInspectorId();
    String getInspectorName();
    String getOfficeName();
}
