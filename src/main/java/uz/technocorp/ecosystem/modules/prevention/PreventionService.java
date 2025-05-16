package uz.technocorp.ecosystem.modules.prevention;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionDto;
import uz.technocorp.ecosystem.modules.prevention.dto.PreventionParamsDto;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionTypeView;
import uz.technocorp.ecosystem.modules.prevention.projection.PreventionView;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileView;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.UUID;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface PreventionService {
    void create(User user, PreventionDto dto);

    // Global
    Page<?> getAll(User user, PreventionParamsDto params);

    PreventionView getById(User user, UUID preventionId);

    List<PreventionTypeView> getTypes();

    // Local
    PreventionView getByIdForInspector(User user, UUID preventionId);

    PreventionView getByIdForCitizen(User user, UUID preventionId);

    Page<PreventionView> getAllByCitizen(User user);

    Page<PreventionView> getAllByInspectorAndPassed(User user, PreventionParamsDto params);

    Page<ProfileView> getAllWithoutPassed(User user, PreventionParamsDto params);
}
