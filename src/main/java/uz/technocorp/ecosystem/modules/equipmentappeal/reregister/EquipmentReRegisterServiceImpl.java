package uz.technocorp.ecosystem.modules.equipmentappeal.reregister;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipmentappeal.reregister.dto.ReRegisterEquipmentDto;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 07.08.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class EquipmentReRegisterServiceImpl implements EquipmentReRegisterService {

    private final EquipmentService equipmentService;
    private final AppealPdfService appealPdfService;
    private final AppealService appealService;

    @Override
    public String reregisterPdf(User user, ReRegisterEquipmentDto dto) {
        // Check and get equipment
        Equipment eq = equipmentService.findByRegistryNumberAndTypeAndActive(dto.getOldRegistryNumber(), dto.getType(), false);

        // Set required fields
        dto.setFactoryNumber(eq.getFactoryNumber());
        dto.setModel(eq.getModel());
        dto.setManufacturedAt(eq.getManufacturedAt());
        dto.setFactory(eq.getFactory());
        dto.setAddress(eq.getAddress());

        // Generate PDF and return path
        return appealPdfService.preparePdfWithParam(dto, user);
    }

    @Override
    public void reregister(User user, SignedAppealDto<ReRegisterEquipmentDto> signDto, HttpServletRequest request) {
        ReRegisterEquipmentDto dto = signDto.getDto();

        // Check equipment
        equipmentService.findByRegistryNumberAndTypeAndActive(dto.getOldRegistryNumber(), dto.getType(), false);

        // Create appeal
        appealService.saveAndSign(user, signDto, request);
    }
}
