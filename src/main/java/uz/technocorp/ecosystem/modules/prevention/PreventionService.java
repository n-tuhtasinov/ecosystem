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

    // Global
    Page<?> getAll(User user, PreventionParamsDto params);

    PreventionView getById(User user, UUID preventionId);

    List<PreventionTypeView> getTypes();

    // Committee
    Page<PreventionView> getAllPassedForCommittee(PreventionParamsDto params);

    Page<ProfileView> getAllWithoutPassedForCommittee(PreventionParamsDto params);

    PreventionView getByIdForCommittee(UUID preventionId);

    // Regional
    Page<PreventionView> getAllPassedForRegional(User user, PreventionParamsDto params);

    Page<ProfileView> getAllWithoutPassedForRegional(User user, PreventionParamsDto params);

    PreventionView getByIdForRegional(User user, UUID preventionId);

    // Inspector
    Page<PreventionView> getAllPassedByInspector(User user, PreventionParamsDto params);

    Page<ProfileView> getAllWithoutPassedForInspector(User user, PreventionParamsDto params);

    PreventionView getByIdForInspector(User user, UUID preventionId);

    void create(User user, PreventionDto dto);

    void deleteById(User user, UUID preventionId);

    // Citizen
    PreventionView getByIdForCitizen(User user, UUID preventionId);

    Page<PreventionView> getAllByCitizen(User user);
}
