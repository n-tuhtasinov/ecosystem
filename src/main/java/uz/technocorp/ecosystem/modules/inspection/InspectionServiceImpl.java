package uz.technocorp.ecosystem.modules.inspection;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.AgreementStatus;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionActDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionUpdateDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectorShortInfo;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionFullDto;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionShortInfo;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionView;
import uz.technocorp.ecosystem.modules.inspection.view.InspectorInfoForInspectionAct;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReportService;
import uz.technocorp.ecosystem.modules.inspectionreport.view.InspectionReportForAct;
import uz.technocorp.ecosystem.modules.user.User;

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
    private final DocumentService documentService;
    private final InspectionReportService inspectionReportService;


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
        inspection.setDecreeDate(dto.decreeDate());
        inspection.setDecreeNumber(dto.decreeNumber());
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
        if (status.equals(InspectionStatus.COMPLETED)) {
            if (inspection.getStatus().equals(InspectionStatus.UNRESOLVED)) {
                inspection.setStatus(InspectionStatus.DELAYED);
            }
        } else {
            inspection.setStatus(status);
        }
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

    @Transactional
    @Override
    public void createAct(User user, SignedReplyDto<InspectionActDto> actDto, HttpServletRequest request) {
        documentService.create(
                new DocumentDto(
                        actDto.getDto().getInspectionId(),
                        DocumentType.ACT,
                        actDto.getFilePath(),
                        actDto.getSign(),
                        Helper.getIp(request),
                        user.getId(),
                        List.of(user.getId()),
                        null)

        );
        updateStatus(user.getId(), InspectionStatus.CONDUCTED);
    }

    @Override
    public String generatePdf(User user, InspectionActDto actDto, HttpServletRequest request) {
        List<InspectorInfoForInspectionAct> inspectors = repository.getAllInspectorInfoByInspectionId(actDto.getInspectionId());
        List<InspectionReportForAct> defects = inspectionReportService.getAllByInspectionId(actDto.getInspectionId());
        return "";
    }
}
