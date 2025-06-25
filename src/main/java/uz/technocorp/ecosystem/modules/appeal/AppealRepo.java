package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealCountParams;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.Map;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.03.2025
 * @since v1.0
 */
public interface AppealRepo {

    Page<AppealCustom> getAppealCustoms(User user, Map<String, String> params, List<AppealType> appealTypes);
    Long countByParams(AppealCountParams params);
}
