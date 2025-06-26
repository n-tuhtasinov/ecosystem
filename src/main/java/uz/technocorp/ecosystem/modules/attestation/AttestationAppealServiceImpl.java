package uz.technocorp.ecosystem.modules.attestation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.view.AppealViewById;
import uz.technocorp.ecosystem.modules.attestation.dto.AttestationPendingParamsDto;
import uz.technocorp.ecosystem.modules.attestation.enums.AttestationStatus;
import uz.technocorp.ecosystem.modules.employee.enums.EmployeeLevel;
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

    @Override
    public Page<AppealViewById> getAllPending(User user, AttestationPendingParamsDto dto) {
        return switch (user.getRole()) {
            case MANAGER, HEAD -> getAllPendingForCommittee(user, dto);
            case REGIONAL -> getAllPendingForRegional(user, dto);
            case INSPECTOR -> getAllPendingForInspector(user, dto);
            case LEGAL -> getAllPendingForLegal(user, dto);
            default -> throw new CustomException("Sizda attestatsiya ko'rish ruhsati yo'q");
        };
    }

    private Page<AppealViewById> getAllPendingForCommittee(User user, AttestationPendingParamsDto dto) {
        // Direction
        Specification<Attestation> hasDirection = specification.hasEmployeeLevel(EmployeeLevel.LEADER);

        // Status
        Specification<Attestation> hasStatus = specification.hasStatus(AttestationStatus.PENDING);

        // Create pageable
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

        Specification<Attestation> spec = where(hasDirection).and(hasStatus);
        Page<UUID> appealIds = repository.findDistinctAppealIds(spec, pageable);

        List<AppealViewById> appeals = appealService.getByIds(appealIds.stream().toList());

        return new PageImpl<>(appeals, pageable, appealIds.getTotalElements());
    }

    private Page<AppealViewById> getAllPendingForRegional(User user, AttestationPendingParamsDto dto) {
        return null;
    }

    private Page<AppealViewById> getAllPendingForInspector(User user, AttestationPendingParamsDto dto) {
        return null;
    }

    private Page<AppealViewById> getAllPendingForLegal(User user, AttestationPendingParamsDto dto) {


        return null;
    }
}
