package uz.technocorp.ecosystem.modules.equipmentappeal.deregister;

import jakarta.servlet.http.HttpServletRequest;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.deregister.dto.DeregisterEquipmentDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 05.08.2025
 * @since v1.0
 */
public interface EquipmentDeregisterService {

    String deregisterPdf(User user, DeregisterEquipmentDto dto);

    void deregister(User user, SignedAppealDto<DeregisterEquipmentDto> signDto, HttpServletRequest request);

}
