package uz.technocorp.ecosystem.modules.attestationappeal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.attestation.Attestation;
import uz.technocorp.ecosystem.modules.attestation.AttestationRepository;
import uz.technocorp.ecosystem.modules.attestation.AttestationSpecification;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;
import uz.technocorp.ecosystem.modules.attestationappeal.dto.AttestationPendingParamsDto;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

/**
 * @author Suxrob
 * @version 1.0
 * @created 26.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AttestationAppealServiceImpl implements AttestationAppealService {

    private final AttestationSpecification specification;
    private final AttestationRepository repository;
    private final AppealService appealService;
    private final ProfileService profileService;

    @Override
    public Page<AppealViewById> getAllPending(User user, AttestationPendingParamsDto dto) {
        return switch (user.getRole()) {
            case MANAGER, HEAD -> getAllPendingForCommittee(dto);
            case REGIONAL -> getAllPendingForRegional(user, dto);
            case INSPECTOR -> getAllPendingForInspector(user, dto);
            case LEGAL -> getAllPendingForLegal(user, dto);
            default -> throw new CustomException("Sizda attestatsiya ko'rish ruhsati yo'q");
        };
    }

    private Page<AppealViewById> getAllPendingForCommittee(AttestationPendingParamsDto dto) {
        // Direction
        Specification<Attestation> hasDirection = specification.hasEmployeeLevel(EmployeeLevel.LEADER);

        Specification<Attestation> spec = where(makeBaseSpecification(dto)).and(hasDirection);

        Page<UUID> appealIds = repository.findDistinctAppealIds(spec, getPageable(dto));
        if (appealIds.getContent().isEmpty()) {
            return Page.empty();
        }
        List<AppealViewById> appeals = appealService.getByIds(appealIds.stream().toList());

        return new PageImpl<>(appeals, getPageable(dto), appealIds.getTotalElements());
    }

    private Page<AppealViewById> getAllPendingForRegional(User user, AttestationPendingParamsDto dto) {
        Integer regionId = profileService.getProfile(user.getProfileId()).getRegionId();
        dto.setRegionId(regionId);

        // Direction
        Specification<Attestation> hasDirection = specification.hasEmployeeLevels(List.of(EmployeeLevel.TECHNICIAN, EmployeeLevel.EMPLOYEE));

        // Inspector
        Specification<Attestation> hasInspector = specification.hasExecutorId(dto.getInspectorId());

        Specification<Attestation> spec = where(makeBaseSpecification(dto)).and(hasDirection).and(hasInspector);
        Page<UUID> appealIds = repository.findDistinctAppealIds(spec, getPageable(dto));
        if (appealIds.getContent().isEmpty()) {
            return Page.empty();
        }
        List<AppealViewById> appeals = appealService.getByIds(appealIds.stream().toList());

        return new PageImpl<>(appeals, getPageable(dto), appealIds.getTotalElements());
    }

    private Page<AppealViewById> getAllPendingForInspector(User user, AttestationPendingParamsDto dto) {
        Integer regionId = profileService.getProfile(user.getProfileId()).getRegionId();
        dto.setRegionId(regionId);

        // Direction
        Specification<Attestation> hasDirection = specification.hasEmployeeLevels(List.of(EmployeeLevel.TECHNICIAN, EmployeeLevel.EMPLOYEE));

        // Inspector
        Specification<Attestation> hasInspector = specification.hasExecutorId(user.getId());

        Specification<Attestation> spec = where(makeBaseSpecification(dto)).and(hasDirection).and(hasInspector);
        Page<UUID> appealIds = repository.findDistinctAppealIds(spec, getPageable(dto));
        if (appealIds.getContent().isEmpty()) {
            return Page.empty();
        }
        List<AppealViewById> appeals = appealService.getByIds(appealIds.stream().toList());

        return new PageImpl<>(appeals, getPageable(dto), appealIds.getTotalElements());
    }

    private Page<AppealViewById> getAllPendingForLegal(User user, AttestationPendingParamsDto dto) {
        Profile profile = profileService.getProfile(user.getProfileId());

        // Tin
        Specification<Attestation> hasLegalTin = specification.hasLegalTin(profile.getTin());

        // Status
        Specification<Attestation> hasStatus = specification.hasStatus(AttestationStatus.PENDING);

        Specification<Attestation> spec = where(hasLegalTin).and(hasStatus);
        Page<UUID> appealIds = repository.findDistinctAppealIds(spec, getPageable(dto));
        if (appealIds.getContent().isEmpty()) {
            return Page.empty();
        }

        List<AppealViewById> appeals = appealService.getByIds(appealIds.stream().toList());

        return new PageImpl<>(appeals, getPageable(dto), appealIds.getTotalElements());
    }

    // Helper methods
    private Specification<Attestation> makeBaseSpecification(AttestationPendingParamsDto dto) {
        // Status
        Specification<Attestation> hasStatus = specification.hasStatus(AttestationStatus.PENDING);

        // Legal
        Specification<Attestation> hasLegal = specification.hasLegal(dto.getSearch());

        // Region
        Specification<Attestation> hasRegion = specification.hasRegionId(dto.getRegionId());

        return where(hasStatus).and(hasLegal).and(hasRegion);
    }

    private Pageable getPageable(AttestationPendingParamsDto dto) {
        return PageRequest.of(dto.getPage() - 1, dto.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
