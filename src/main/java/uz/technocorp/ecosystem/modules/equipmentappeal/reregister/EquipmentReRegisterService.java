package uz.technocorp.ecosystem.modules.equipmentappeal.reregister;


import jakarta.servlet.http.HttpServletRequest;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.equipmentappeal.reregister.dto.ReRegisterEquipmentDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 07.08.2025
 * @since v1.0
 */
public interface EquipmentReRegisterService {

    String reregisterPdf(User user, ReRegisterEquipmentDto dto);

    void reregister(User user, SignedAppealDto<ReRegisterEquipmentDto> signDto, HttpServletRequest request);
}
