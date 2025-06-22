package uz.technocorp.ecosystem.modules.riskassessment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.riskassessment.projection.RiskAssessmentView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.security.CurrentUser;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.04.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class RiskAssessmentServiceImpl implements RiskAssessmentService {
    private final RiskAssessmentRepository repository;
    private final ProfileService profileService;
    private final OfficeService officeService;


    @Override
    public Page<RiskAssessmentView> getAllHf(@CurrentUser User user, Long tin, Integer regionId, Integer intervalId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "sum_score");
        Role role = user.getRole();
        if (role.equals(Role.REGIONAL)) {
            Profile profile = profileService.getProfile(user.getProfileId());
            Office office = officeService.findById(profile.getOfficeId());
            return repository.getAllHfByTinAndRegionId(pageable, office.getRegionId(), intervalId, tin);
        } else if (role.equals(Role.INSPECTOR)) {
            return repository.getAllHfByTinAndInspectorId(pageable, user.getId(), intervalId, tin);
        } else {
            return repository.getAllHfByTin(pageable, intervalId, tin);
        }
    }

    @Override
    public Page<RiskAssessmentView> getAllIrs(@CurrentUser User user, Long tin, Integer regionId, Integer intervalId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "sum_score");
        Role role = user.getRole();
        if (role.equals(Role.REGIONAL)) {
            Profile profile = profileService.getProfile(user.getProfileId());
            Office office = officeService.findById(profile.getOfficeId());
            return repository.getAllIrsByTinAndRegionId(pageable, office.getRegionId(), intervalId, tin);
        } else if (role.equals(Role.INSPECTOR)) {
            return repository.getAllIrsByTinAndInspectorId(pageable, user.getId(), intervalId, tin);
        } else {
            return repository.getAllIrsByTin(pageable, intervalId, tin);
        }
    }

    @Override
    public Page<RiskAssessmentView> getAllEquipments(@CurrentUser User user, Long tin, Integer regionId, Integer intervalId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "sum_score");
        Role role = user.getRole();
        if (role.equals(Role.REGIONAL)) {
            Profile profile = profileService.getProfile(user.getProfileId());
            Office office = officeService.findById(profile.getOfficeId());
            return repository.getAllEquipmentsByTinAndRegionId(pageable, office.getRegionId(), intervalId, tin);
        } else if (role.equals(Role.INSPECTOR)) {
            return repository.getAllEquipmentsByTinAndInspectorId(pageable, user.getId(), intervalId, tin);
        } else {
            return repository.getAllEquipmentsByTin(pageable, intervalId, tin);
        }
    }
}
