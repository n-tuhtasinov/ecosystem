package uz.technocorp.ecosystem.modules.equipmentappeal.deregister;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipmentappeal.deregister.dto.DeregisterEquipmentDto;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 05.08.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class EquipmentDeregisterServiceImpl implements EquipmentDeregisterService {

    private final EquipmentService equipmentService;
    private final AppealPdfService appealPdfService;
    private final ProfileService profileService;
    private final AppealService appealService;

    @Override
    public String deregisterPdf(User user, DeregisterEquipmentDto dto) {
        // Get profile identity
        Long identity = profileService.getProfileIdentity(user.getProfileId());

        // Check and get equipment
        Equipment eq = equipmentService.findByRegistryNumberAndOwnerAndActive(dto.getRegistryNumber(), identity, dto.getType(), true);

        // Set required fields
        dto.setFactoryNumber(eq.getFactoryNumber());
        dto.setModel(eq.getModel());

        // Generate PDF and return path
        return appealPdfService.preparePdfWithParam(dto, user);
    }

    @Override
    public void deregister(User user, SignedAppealDto<DeregisterEquipmentDto> signDto, HttpServletRequest request) {
        DeregisterEquipmentDto dto = signDto.getDto();

        // Get profile identity
        Long identity = profileService.getProfileIdentity(user.getProfileId());

        // Check and get equipment
        Equipment eq = equipmentService.findByRegistryNumberAndOwnerAndActive(dto.getRegistryNumber(), identity, dto.getType(), true);

        // Set address information
        dto.setRegionId(eq.getRegionId());
        dto.setDistrictId(eq.getDistrictId());
        dto.setAddress(eq.getAddress());

        // Create appeal
        appealService.saveAndSign(user, signDto, request);
    }

}
