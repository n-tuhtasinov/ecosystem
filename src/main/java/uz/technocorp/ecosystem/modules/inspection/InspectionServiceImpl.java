package uz.technocorp.ecosystem.modules.inspection;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionActDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionUpdateDto;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class InspectionServiceImpl implements InspectionService {

    private final InspectionRepository repository;

    @Override
    public void update(UUID id, InspectionDto dto) {
        Inspection inspection = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        inspection.setDecreePath(dto.decreePath());
        inspection.setTin(dto.tin());
        inspection.setEndDate(dto.endDate());
        inspection.setStartDate(dto.startDate());
        inspection.setInspectorIds(dto.inspectorIdList());
        inspection.setIntervalId(dto.intervalId());
        inspection.setStatus(InspectionStatus.IN_PROCESS);
        repository.save(inspection);
    }

    @Override
    public void update(UUID id, InspectionUpdateDto dto) {
        Inspection inspection = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        inspection.setMeasuresPath(dto.measuresPath());
        inspection.setNotificationLetterDate(dto.notificationLetterDate());
        inspection.setNotificationLetterPath(dto.notificationLetterPath());
        inspection.setSchedulePath(dto.schedulePath());
        inspection.setSpecialCode(dto.specialCode());
        inspection.setProgramPath(dto.programPath());
        inspection.setResultPath(dto.resultPath());
        inspection.setOrderPath(dto.orderPath());
        repository.save(inspection);
    }

    @Override
    public void update(UUID id, InspectionActDto dto) {
        Inspection inspection = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        inspection.setActPath(dto.actPath());
        repository.save(inspection);
    }

    @Override
    public Page<InspectionCustom> getAll(User user, int page, int size, Long tin, InspectionStatus status, Integer intervalId) {
        return repository.getInspectionCustoms(user, page, size, tin, status, intervalId);
    }
}
