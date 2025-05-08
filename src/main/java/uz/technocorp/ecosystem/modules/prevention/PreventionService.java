package uz.technocorp.ecosystem.modules.prevention;

import uz.technocorp.ecosystem.modules.prevention.dto.PagingDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface PreventionService {
    void create(User user, PreventionDto dto);

    PagingDto<PreventionDto> getOrganizationsWithEvents(User user, PreventionParamsDto params);

    PagingDto<PreventionDto> getOrganizationsWithoutEvents(User user, PreventionParamsDto params);
}
