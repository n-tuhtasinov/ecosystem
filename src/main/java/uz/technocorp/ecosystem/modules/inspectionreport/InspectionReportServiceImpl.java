package uz.technocorp.ecosystem.modules.inspectionreport;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.inspectionreport.dto.InspectionReportDto;
import uz.technocorp.ecosystem.modules.inspectionreport.view.InspectionReportView;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.enums.InspectionReportExecutionStatus;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class InspectionReportServiceImpl implements InspectionReportService {

    private final InspectionReportRepository repository;

    @Override
    public void create(UUID inspectionId, InspectionReportDto dto) {
        repository.save(
                InspectionReport
                        .builder()
                        .inspectionId(inspectionId)
                        .assignedTasks(dto.assignedTasks())
                        .deadline(dto.deadline())
                        .build()
        );
    }

    @Override
    public void update(User user, UUID id, InspectionReportDto dto) {
        InspectionReport inspectionReport = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv ijro hisoboti", "Id", id));
        UUID inspectorId = inspectionReport.getCreatedBy();
        if (user.getId().equals(inspectorId)) {
            inspectionReport.setAssignedTasks(dto.assignedTasks());
            inspectionReport.setDeadline(dto.deadline());
            repository.save(inspectionReport);
        }
    }

    @Override
    public void confirm(User user, UUID id) {
        InspectionReport inspectionReport = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv ijro hisoboti", "Id", id));
        UUID inspectorId = inspectionReport.getCreatedBy();
        if (user.getId().equals(inspectorId)) {
            UUID inspectionId = inspectionReport.getInspectionId();
        }
    }

    @Override
    public Page<InspectionReportView> getAllByInspectionId(User user, UUID inspectionId, int page, int size, boolean eliminated) {
        Pageable pageable = PageRequest.of(page-1, size);
        if (user.getRole().equals(Role.INSPECTOR)) {
            return repository.findAlByInspectionIdAndInspectorId(user.getId(), inspectionId, pageable);
        }
        return repository.findAlByInspectionId(inspectionId, pageable, eliminated);
    }

}
