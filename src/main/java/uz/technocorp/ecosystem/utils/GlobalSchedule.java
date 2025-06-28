package uz.technocorp.ecosystem.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.modules.document.DocumentRepository;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.inspection.Inspection;
import uz.technocorp.ecosystem.modules.inspection.InspectionRepository;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReportRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisIntervalRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 26.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class GlobalSchedule {
    
    private final InspectionRepository inspectionRepository;
    private final InspectionReportRepository inspectionReportRepository;
    private final RiskAnalysisIntervalRepository intervalRepository;
    private final DocumentRepository documentRepository;
    
    // TODO tekshiruvlarni har kuni 00:01 da EXPIRED ga tekshirish
    public void wasInspectionConducted(){
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new RuntimeException("Xavf tahlil qilish davomiyligi"));
        List<Inspection> inspections = inspectionRepository.findAllByIntervalId(riskAnalysisInterval.getId());
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Inspection> endingTodayInspections = inspections.stream().filter(inspection -> inspection.getEndDate().equals(yesterday)).toList();
        endingTodayInspections.forEach(inspection -> {
            boolean empty = documentRepository
                    .findByBelongIdAndDocumentType(inspection.getId(), DocumentType.ACT)
                    .isEmpty();
            if (empty) {
                inspection.setStatus(InspectionStatus.EXPIRED);
                inspectionRepository.save(inspection);
            }
        });
    }

    // TODO tekshiruvlarni har kuni 00:01 da UNRESOLVED ga tekshirish
    public void checkingInspectionReportExecuted() {
        // buni logikasini kelishib olish kerak chunki har xil variantlar mavjud
    }

}
