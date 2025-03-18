package uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal;

import uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.dto.HazardousFacilityRegistrationAppealDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityregistrationappeal.helper.HazardousFacilityRegistrationAppealHelper;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.publics.AttachmentDto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface HazardousFacilityRegistrationAppealService {

    void create(User user, HazardousFacilityRegistrationAppealDto dto);
    void update(UUID id, HazardousFacilityRegistrationAppealDto dto);
    HazardousFacilityRegistrationAppealHelper getById(UUID id);
    void setAttachments(AttachmentDto dto);

}
