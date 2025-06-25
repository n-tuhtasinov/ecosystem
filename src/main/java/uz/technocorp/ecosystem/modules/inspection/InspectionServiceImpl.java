package uz.technocorp.ecosystem.modules.inspection;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionUpdateDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectorShortInfo;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionFullDto;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionShortInfo;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionView;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.dto.InspectorDto;

import java.time.LocalDate;
import java.util.List;
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
    private final ObjectMapper objectMapper;

    @Override
    public void update(UUID id, InspectionDto dto) {
        Inspection inspection = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        inspection.setTin(dto.tin());
        inspection.setEndDate(dto.endDate());
        inspection.setStartDate(dto.startDate());
        inspection.setDecreePath(dto.decreePath());
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
    public void updateStatus(UUID id, InspectionStatus status) {
        Inspection inspection = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        inspection.setStatus(status);
        repository.save(inspection);
    }

    @Override
    public Page<InspectionCustom> getAll(User user, int page, int size, Long tin, InspectionStatus status, Integer intervalId) {
        return repository.getInspectionCustoms(user, page, size, tin, status, intervalId);
    }

    @Override
    public InspectionFullDto getById(UUID id) {
        InspectionView view = repository
                .getInspectionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        List<InspectorShortInfo> inspectors;
        try {
            inspectors = objectMapper.readValue(
                    view.getInspectors(),
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse inspectors JSON", e);
        }


        return new InspectionFullDto(
                view.getId(),
                view.getStartDate(),
                view.getEndDate(),
                view.getStatus(),
                view.getSpecialCode(),
                view.getDecreePath(),
                view.getSchedulePath(),
                view.getNotificationLetterPath(),
                view.getNotificationLetterDate(),
                view.getOrderPath(),
                view.getProgramPath(),
                view.getMeasuresPath(),
                view.getResultPath(),
                inspectors
        );
    }

    @Override
    public List<InspectionShortInfo> getAllByInspector(User user, LocalDate startDate, LocalDate endDate) {
        return repository.getAllByInspectorId(user.getId(), startDate, endDate, InspectionStatus.IN_PROCESS.name());
    }
}
