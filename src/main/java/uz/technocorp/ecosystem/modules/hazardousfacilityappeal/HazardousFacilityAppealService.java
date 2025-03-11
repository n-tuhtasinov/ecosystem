package uz.technocorp.ecosystem.modules.hazardousfacilityappeal;

import uz.technocorp.ecosystem.modules.hazardousfacilityappeal.helper.HazardousFacilityAppealDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.publics.AttachmentDto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface HazardousFacilityAppealService {

    void create(User user, uz.technocorp.ecosystem.modules.hazardousfacilityappeal.dto.HazardousFacilityAppealDto dto);
    void update(UUID id, uz.technocorp.ecosystem.modules.hazardousfacilityappeal.dto.HazardousFacilityAppealDto dto);
    HazardousFacilityAppealDto getById(UUID id);
    void setAttachments(AttachmentDto dto);

}
