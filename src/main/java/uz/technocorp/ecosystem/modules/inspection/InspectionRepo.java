package uz.technocorp.ecosystem.modules.inspection;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.05.2025
 * @since v1.0
 */
public interface InspectionRepo {

    Page<InspectionCustom> getInspectionCustoms(User user, int page, int size, Long tin, InspectionStatus status, Integer intervalId);
}
