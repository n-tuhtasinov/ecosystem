package uz.technocorp.ecosystem.modules.appeal;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealService {

    void setInspector(UUID inspector_id, UUID appeal_id);
}
