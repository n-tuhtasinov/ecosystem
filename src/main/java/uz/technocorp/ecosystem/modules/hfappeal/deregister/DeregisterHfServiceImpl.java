package uz.technocorp.ecosystem.modules.hfappeal.deregister;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedAppealDto;
import uz.technocorp.ecosystem.modules.appeal.pdfservice.AppealPdfService;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.hfappeal.deregister.dto.HfDeregisterDto;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.User;

/**
 * @author Suxrob
 * @version 1.0
 * @created 18.08.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class DeregisterHfServiceImpl implements DeregisterHfService {

    private final HazardousFacilityService hfService;
    private final ProfileService profileService;
    private final AppealPdfService pdfService;
    private final AppealService appealService;

    @Override
    public String deregisterPdf(User user, HfDeregisterDto dto) {
        // Get profile legalTin
        Long legalTin = profileService.getProfileIdentity(user.getProfileId());

        // Check and get HF
        HazardousFacility hf = hfService.findByRegistryNumberAndLegalTinAndActive(dto.getRegistryNumber(), legalTin, true);

        // Set required fields
        dto.setAddress(hf.getAddress());
        dto.setHfName(hf.getName());

        return pdfService.preparePdfWithParam(dto, user);
    }

    @Override
    public void deregister(User user, SignedAppealDto<HfDeregisterDto> signDto, HttpServletRequest request) {
        HfDeregisterDto dto = signDto.getDto();

        // Get profile identity
        Long legalTin = profileService.getProfileIdentity(user.getProfileId());

        // Check and get HF
        HazardousFacility hf = hfService.findByRegistryNumberAndLegalTinAndActive(dto.getRegistryNumber(), legalTin, true);

        // Set required fields
        dto.setHfName(hf.getName());
        dto.setRegionId(hf.getRegionId());
        dto.setDistrictId(hf.getDistrictId());
        dto.setAddress(hf.getAddress());

        // Create appeal
        appealService.saveAndSign(user, signDto, request);
    }
}
