package uz.technocorp.ecosystem.modules.hazardousfacility;

import java.util.Map;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
public interface HazardousFacilityService {

    void create(UUID id);
    void deActivate(UUID id, Map<String, String> dto);
    void periodicUpdate(UUID id, Map<String, String> dto);
}
