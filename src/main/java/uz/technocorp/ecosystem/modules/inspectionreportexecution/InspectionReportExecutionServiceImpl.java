package uz.technocorp.ecosystem.modules.inspectionreportexecution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReport;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReportRepository;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReportService;
import uz.technocorp.ecosystem.modules.inspectionreport.dto.InspectionReportDto;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.dto.IRExecutionDto;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.enums.InspectionReportExecutionStatus;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class InspectionReportExecutionServiceImpl implements InspectionReportExecutionService {

    private final InspectionReportExecutionRepository repository;

    @Override
    public void create(UUID reportId, IRExecutionDto dto) {
        repository.save(
                InspectionReportExecution
                        .builder()
                        .reportId(reportId)
                        .status(InspectionReportExecutionStatus.IN_PROCESS)
                        .fileUploadDate(LocalDate.now())
                        .executionFilePath(dto.paramValue())
                        .build()
        );
    }

    @Override
    public void reject(User user, UUID id, IRExecutionDto dto) {
        InspectionReportExecution inspectionReportExecution = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv ijro hisoboti", "Id", id));
        UUID inspectorId = inspectionReportExecution.getCreatedBy();
        if (user.getId().equals(inspectorId)) {
            inspectionReportExecution.setRejectedCause(dto.paramValue());
            inspectionReportExecution.setStatus(InspectionReportExecutionStatus.REJECTED);
            repository.save(inspectionReportExecution);
        }
    }

    @Override
    public void accept(User user, UUID id) {
        InspectionReportExecution inspectionReportExecution = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv ijro hisoboti", "Id", id));
        UUID inspectorId = inspectionReportExecution.getCreatedBy();
        if (user.getId().equals(inspectorId)) {
            inspectionReportExecution.setStatus(InspectionReportExecutionStatus.ACCEPTED);
            repository.save(inspectionReportExecution);
        }
    }
}
