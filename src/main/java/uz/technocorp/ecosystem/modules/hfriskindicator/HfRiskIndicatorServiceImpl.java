package uz.technocorp.ecosystem.modules.hfriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisIntervalRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;
import uz.technocorp.ecosystem.modules.riskassessment.RiskAssessment;
import uz.technocorp.ecosystem.modules.riskassessment.RiskAssessmentRepository;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.HFRIndicatorDto;
import uz.technocorp.ecosystem.modules.riskassessment.enums.RiskAssessmentIndicator;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;

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
public class HfRiskIndicatorServiceImpl implements HfRiskIndicatorService {

    private final HfRiskIndicatorRepository repository;
    private final HazardousFacilityRepository hazardousFacilityRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskAnalysisIntervalRepository intervalRepository;

    @Override
    public void create(HFRIndicatorDto dto) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));
        List<HfRiskIndicator> allByQuarter = repository.findAllByQuarter(riskAnalysisInterval.getId(), dto.hazardousFacilityId());
        if (allByQuarter.isEmpty()) {
            if (!dto.indicatorType().equals(RiskAssessmentIndicator.PARAGRAPH_HF_1)) {
                HazardousFacility hazardousFacility = hazardousFacilityRepository
                        .findById(dto.hazardousFacilityId())
                        .orElseThrow(() -> new ResourceNotFoundException("XICHO", "Id", dto.hazardousFacilityId()));
               String identificationCardPath = hazardousFacility.getIdentificationCardPath();
                String expertOpinionPath = hazardousFacility.getExpertOpinionPath();
                String industrialSafetyDeclarationPath = hazardousFacility.getIndustrialSafetyDeclarationPath();
                String insurancePolicyPath = hazardousFacility.getInsurancePolicyPath();
                StringBuilder descriptionBuilder = new StringBuilder();
                if (identificationCardPath.isEmpty()) {
                    descriptionBuilder.append("Identifikatsiya xulosasi mavjud emas. ");
                }
                if (expertOpinionPath.isEmpty()) {
                    descriptionBuilder.append("Ekspertiza xulosasi mavjud emas. ");
                }
                if (industrialSafetyDeclarationPath.isEmpty()) {
                    descriptionBuilder.append("Sanoat xavfsizligi deklaratsiyasi mavjud emas. ");
                }
                if (insurancePolicyPath.isEmpty()) {
                    descriptionBuilder.append("Majburiy sug'urta polisi mavjud emas. ");
                }
                repository.save(
                        HfRiskIndicator
                                .builder()
                                .hazardousFacilityId(dto.hazardousFacilityId())
                                .indicatorType(RiskAssessmentIndicator.PARAGRAPH_HF_1)
                                .score(RiskAssessmentIndicator.PARAGRAPH_HF_1.getScore())
                                .description(descriptionBuilder.toString())
                                .tin(dto.tin())
                                .riskAnalysisInterval(riskAnalysisInterval)
                                .build()
                );
            }
            HfRiskIndicator existRiskIndicator = allByQuarter.stream().filter(riskIndicator -> riskIndicator.getIndicatorType().equals(dto.indicatorType())).toList().getFirst();
            if (existRiskIndicator != null) {
                throw new RuntimeException("Ushbu ko'rsatkich bo'yicha ma'lumot kiritilgan!");
            }
            repository.save(
                    HfRiskIndicator
                            .builder()
                            .hazardousFacilityId(dto.hazardousFacilityId())
                            .indicatorType(dto.indicatorType())
                            .score(dto.indicatorType().getScore())
                            .description(dto.description())
                            .tin(dto.tin())
                            .build()
            );
        }
    }

    @Override
    public void update(UUID id, HFRIndicatorDto dto) {
        HfRiskIndicator riskIndicator = repository
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
    public List<RiskIndicatorView> findAllByHFIdAndTin(UUID id, Long tin) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

        return repository.findAllByHfIdAndTinAndDate(id, tin, riskAnalysisInterval.getId());
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

        List<RiskAssessmentDto> allGroupByHazardousFacilityAndTin = repository.findAllGroupByHfIdAndTin(riskAnalysisInterval.getId());
        // Barcha qiymatlarni guruhlash: TIN + hazardousFacilityId
        Map<Short, List<RiskAssessmentDto>> groupedByTin = allGroupByHazardousFacilityAndTin.stream()
                .collect(Collectors.groupingBy(RiskAssessmentDto::tin));

        // Har bir TIN uchun hisoblash
        for (Map.Entry<Short, List<RiskAssessmentDto>> entry : groupedByTin.entrySet()) {
            Short tin = entry.getKey();
            List<RiskAssessmentDto> dtoList = entry.getValue();

            // Null bo'lgan va bo'lmaganlarni ajratib olish
            Optional<RiskAssessmentDto> nullFacility = dtoList.stream()
                    .filter(dto -> dto.objectId() == null)
                    .findFirst();

            int nullScore = nullFacility.map(RiskAssessmentDto::sumScore).orElse(0);

            // Endi null bo'lmaganlarga qo‘shib saqlaymiz
            dtoList.stream()
                    .filter(dto -> dto.objectId() != null)
                    .forEach(dto -> {
                        riskAssessmentRepository.save(
                                RiskAssessment.builder()
                                        .sumScore(dto.sumScore() + nullScore)
                                        .objectName(
                                                hazardousFacilityRepository.findById(dto.objectId())
                                                        .map(HazardousFacility::getName)
                                                        .orElse("Nomi ma'lum emas.")

                                        )
                                        .tin(tin)
                                        .hazardousFacilityId(dto.objectId())
                                        .build()
                        );
                    });
        }

    }

}
