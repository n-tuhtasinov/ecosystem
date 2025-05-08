package uz.technocorp.ecosystem.modules.attractionriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.elevatorriskindicator.dto.EquipmentRiskIndicatorDto;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentRepository;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisIntervalRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;
import uz.technocorp.ecosystem.modules.riskassessment.RiskAssessment;
import uz.technocorp.ecosystem.modules.riskassessment.RiskAssessmentRepository;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AttractionRiskIndicatorServiceImpl implements AttractionRiskIndicatorService {

    private final AttractionRiskIndicatorRepository repository;
    private final EquipmentRepository equipmentRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskAnalysisIntervalRepository intervalRepository;

    @Override
    public void create(EquipmentRiskIndicatorDto dto) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));
        List<AttractionRiskIndicator> allByQuarter = repository.findAllByQuarter(riskAnalysisInterval.getId(), dto.equipmentId());
        if (!allByQuarter.isEmpty()) {
            AttractionRiskIndicator existRiskIndicator = allByQuarter
                    .stream()
                    .filter(riskIndicator -> riskIndicator
                            .getIndicatorType()
                            .equals(dto.indicatorType()))
                    .toList()
                    .getFirst();
            if (existRiskIndicator != null) {
                throw new RuntimeException("Ushbu ko'rsatkich bo'yicha ma'lumot kiritilgan!");
            }

        }
        repository.save(
                AttractionRiskIndicator
                        .builder()
                        .equipmentId(dto.equipmentId())
                        .indicatorType(dto.indicatorType())
                        .score(dto.indicatorType().getScore())
                        .description(dto.description())
                        .tin(dto.tin())
                        .build()
        );

    }

    @Override
    public void update(UUID id, EquipmentRiskIndicatorDto dto) {
        AttractionRiskIndicator riskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasi", "Id", id));
        riskIndicator.setDescription(dto.description());
        repository.save(riskIndicator);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<RiskIndicatorView> findAllByEquipmentIdAndTin(UUID id, Long tin) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

        return repository.findAllByEquipmentIdAndTinAndDate(id, tin, riskAnalysisInterval.getId());
    }

    @Override
    public List<RiskIndicatorView> findAllByTin(Long tin) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));
        return repository.findAllByTinAndDate(tin, riskAnalysisInterval.getId());
    }

    @Scheduled(cron = "0 0 22 31 3 *")  // 31-mart 10:00 da
    @Scheduled(cron = "0 0 22 30 6 *")  // 30-iyun 10:00 da
    @Scheduled(cron = "0 0 22 30 9 *")  // 30-sentyabr 10:00 da
    @Scheduled(cron = "0 0 22 31 12 *") // 31-dekabr 10:00 da
    public void sumScore() {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

        List<RiskAssessmentDto> allGroupByEquipmentAndTin = repository.findAllGroupByEquipmentAndTin(riskAnalysisInterval.getId());
        // Barcha qiymatlarni guruhlash: TIN + EquipmentId
        Map<Short, List<RiskAssessmentDto>> groupedByTin = allGroupByEquipmentAndTin.stream()
                .collect(Collectors.groupingBy(RiskAssessmentDto::tin));

        // Har bir TIN uchun hisoblash
        for (Map.Entry<Short, List<RiskAssessmentDto>> entry : groupedByTin.entrySet()) {
            Short tin = entry.getKey();
            List<RiskAssessmentDto> dtoList = entry.getValue();

            // Null bo'lgan va bo'lmaganlarni ajratib olish
            Optional<RiskAssessmentDto> nullEquipment = dtoList.stream()
                    .filter(dto -> dto.objectId() == null)
                    .findFirst();

            int nullScore = nullEquipment.map(RiskAssessmentDto::sumScore).orElse(0);

            // Endi null bo'lmaganlarga qo‘shib saqlaymiz
            dtoList.stream()
                    .filter(dto -> dto.objectId() != null)
                    .forEach(dto -> {
                        riskAssessmentRepository.save(
                                RiskAssessment.builder()
                                        .sumScore(dto.sumScore() + nullScore)
                                        .objectName(
                                                equipmentRepository.findById(dto.objectId())
                                                        .map(Equipment::getRegistryNumber)
                                                        .orElse("Nomi ma'lum emas.")

                                        )
                                        .tin(tin)
                                        .equipmentId(dto.objectId())
                                        .build()
                        );
                    });
        }

    }

}
