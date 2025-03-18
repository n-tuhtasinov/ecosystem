package uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal;

import uz.technocorp.ecosystem.modules.hazardousfacilitymodificationappeal.dto.HazardousFacilityModificationAppealDto;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface HazardousFacilityModificationAppealService {

    void create(User user, HazardousFacilityModificationAppealDto dto);
    void update(UUID id, HazardousFacilityModificationAppealDto dto);
//    HazardousFacilityRegistrationAppealHelper getById(UUID id);
//    void setAttachments(AttachmentDto dto);

}
