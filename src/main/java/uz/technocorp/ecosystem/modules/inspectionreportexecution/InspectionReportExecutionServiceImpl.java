package uz.technocorp.ecosystem.modules.inspectionreportexecution;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.inspection.InspectionService;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReport;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReportRepository;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReportService;
import uz.technocorp.ecosystem.modules.inspectionreport.dto.InspectionReportDto;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.dto.IRExecutionDto;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.enums.InspectionReportExecutionStatus;
import uz.technocorp.ecosystem.modules.inspectionreportexecution.view.InspectionReportExecutionView;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    private final InspectionReportRepository inspectionReportRepository;
    private final InspectionService inspectionService;

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

        InspectionReport inspectionReport = inspectionReportRepository
                .findById(inspectionReportExecution.getReportId())
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv ijro hisoboti", "Id", id));
        UUID inspectorId = inspectionReport.getCreatedBy();
        if (user.getId().equals(inspectorId)) {
            inspectionReportExecution.setRejectedCause(dto.paramValue());
            inspectionReportExecution.setStatus(InspectionReportExecutionStatus.REJECTED);
            repository.save(inspectionReportExecution);
        }
    }

    @Override
    public void accept(User user, UUID id) {
        InspectionReportExecution inspectionReportExecution = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv ijro hisoboti", "Id", id));


        InspectionReport inspectionReport = inspectionReportRepository
                .findById(inspectionReportExecution.getReportId())
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv ijro hisoboti", "Id", id));

        UUID inspectorId = inspectionReport.getCreatedBy();
        if (user.getId().equals(inspectorId)) {
            inspectionReportExecution.setStatus(InspectionReportExecutionStatus.ACCEPTED);
            repository.save(inspectionReportExecution);
            Integer countNotEliminatedReports = inspectionReportRepository.getCountNotEliminatedReports(inspectionReport.getInspectionId());
            inspectionReport.setEliminated(true);
            inspectionReportRepository.save(inspectionReport);
            if (countNotEliminatedReports.equals(1)) {
                inspectionService.updateStatus(inspectionReport.getInspectionId(), InspectionStatus.COMPLETED);
            }

        }
        throw new ResourceNotFoundException("Tekshiruv ijro hisoboti siz uchun", "Id", id);
    }

    @Override
    public List<InspectionReportExecutionView> getAllByInspectionReportId(UUID reportId) {
        return repository.getAllByReportId(reportId);
    }
}
