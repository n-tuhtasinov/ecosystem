package uz.technocorp.ecosystem.modules.cadastrepassport;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.cadastrepassport.dto.CadastrePassportParams;
import uz.technocorp.ecosystem.modules.cadastrepassport.view.CadastrePassportView;
import uz.technocorp.ecosystem.modules.cadastrepassportappeal.dto.CadastrePassportDto;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.utils.JsonParser;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class CadastrePassportServiceImpl implements CadastrePassportService {

    private final CadastrePassportRepository repository;
    private final ProfileService profileService;

    @Override
    public void create(Appeal appeal, String registryNumber) {

        CadastrePassportDto dto = JsonParser.parseJsonData(appeal.getData(), CadastrePassportDto.class);

        CadastrePassport passport = CadastrePassport.builder()
                .legalTin(appeal.getOwnerIdentity())
                .legalName(appeal.getOwnerName())
                .legalAddress(appeal.getOwnerAddress())
                .hfId(dto.getHfId())
                .hfName(dto.getHfName())
                .appealId(appeal.getId())
                .hfRegionId(appeal.getRegionId())
                .hfDistrictId(appeal.getDistrictId())
                .hfAddress(appeal.getAddress())
                .registryNumber(registryNumber)
                .organizationTin(dto.getOrganizationTin())
                .organizationName(dto.getOrganizationName())
                .organizationAddress(dto.getOrganizationAddress())
                .location(dto.getLocation())
                .files(dto.getFiles())
                .build();

        repository.save(passport);
    }

    @Override
    public Page<CadastrePassportView> getAll(User user, CadastrePassportParams params) {

        Profile profile = profileService.getProfile(user.getProfileId());

        switch (user.getRole()) {
            case MANAGER, HEAD -> changeParams(params, null);
            case LEGAL -> changeParams(params, profile.getIdentity());
            default -> throw new RuntimeException("Sizda kadastrni ko'rish ruxsati yo'q");
        }

        return repository.getAllByParams(params);
    }

    @Override
    public CadastrePassport getById(UUID passportId) {
        return repository.findById(passportId).orElseThrow(() -> new ResourceNotFoundException("Kadastr passporti", "ID", passportId));
    }

    private void changeParams(CadastrePassportParams params, Long legalTin) {
        if (legalTin != null) {
            params.setLegalTin(legalTin);
            return;
        }

        if (isTin(params.getSearch())) {
            params.setLegalTin(Long.valueOf(params.getSearch()));
            params.setSearch(null);
        }
    }

    private Boolean isTin(String search) {
        try {
            if (search.length() == 9) {
                Long.parseLong(search);
                return true;
            }
        } catch (RuntimeException ignored) {
        }
        return false;
    }
}
