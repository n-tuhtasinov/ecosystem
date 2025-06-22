package uz.technocorp.ecosystem.modules.equipment;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentParams;
import uz.technocorp.ecosystem.modules.equipment.view.EquipmentView;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
public interface EquipmentRepo {

    Page<EquipmentView> getAllByParams(User user, EquipmentParams params);

    Long countByParams(Long legalTin, Integer regionId);
}
