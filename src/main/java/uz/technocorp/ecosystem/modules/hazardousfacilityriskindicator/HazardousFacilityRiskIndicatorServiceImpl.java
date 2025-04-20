package uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacility;
import uz.technocorp.ecosystem.modules.hazardousfacility.HazardousFacilityRepository;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment.HazardousFacilityRiskAssessment;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment.HazardousFacilityRiskAssessmentRepository;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskassessment.dto.HFRAssessmentDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.dto.HFRIndicatorDto;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.enums.HazardousFacilityRiskIndicatorType;
import uz.technocorp.ecosystem.modules.hazardousfacilityriskindicator.view.HFRIView;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class HazardousFacilityRiskIndicatorServiceImpl implements HazardousFacilityRiskIndicatorService {

    private final HazardousFacilityRiskIndicatorRepository repository;
    private final HazardousFacilityRepository hazardousFacilityRepository;
    private final HazardousFacilityRiskAssessmentRepository riskAssessmentRepository;

    @Override
    public void create(HFRIndicatorDto dto) {
        LocalDate date = LocalDate.now();
        LocalDate quarterStart = getQuarterStart(date);
        LocalDate quarterEnd = getQuarterEnd(date);
        LocalDateTime startDateTime = quarterStart.atStartOfDay();
        LocalDateTime endDateTime = quarterEnd.atStartOfDay();

        List<HazardousFacilityRiskIndicator> allByQuarter = repository.findAllByQuarter(startDateTime, endDateTime, dto.hazardousFacilityId());
        if (allByQuarter.isEmpty()) {
            if (!dto.indicatorType().equals(HazardousFacilityRiskIndicatorType.PARAGRAPH_1)) {
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
                        HazardousFacilityRiskIndicator
                                .builder()
                                .hazardousFacilityId(dto.hazardousFacilityId())
                                .indicatorType(HazardousFacilityRiskIndicatorType.PARAGRAPH_1)
                                .score(HazardousFacilityRiskIndicatorType.PARAGRAPH_1.getScore())
                                .description(descriptionBuilder.toString())
                                .tin(dto.tin())
                                .build()
                );
            }
            HazardousFacilityRiskIndicator existRiskIndicator = allByQuarter.stream().filter(riskIndicator -> riskIndicator.getIndicatorType().equals(dto.indicatorType())).toList().getFirst();
            if (existRiskIndicator != null) {
                throw new RuntimeException("Ushbu ko'rsatkich bo'yicha ma'lumot kiritilgan!");
            }
            repository.save(
                    HazardousFacilityRiskIndicator
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
        HazardousFacilityRiskIndicator riskIndicator = repository
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
    public List<HFRIView> findAllByHazardousFacilityId(UUID id) {
        LocalDate date = LocalDate.now();
        LocalDate quarterStart = getQuarterStart(date);
        LocalDate quarterEnd = getQuarterEnd(date);
        LocalDateTime startDateTime = quarterStart.atStartOfDay();
        LocalDateTime endDateTime = quarterEnd.atStartOfDay();
        return repository.findAllByHazardousFacilityIdAndDate(id, startDateTime, endDateTime);
    }

    @Override
    public List<HFRIView> findAllByTin(Long tin) {
        LocalDate date = LocalDate.now();
        LocalDate quarterStart = getQuarterStart(date);
        LocalDate quarterEnd = getQuarterEnd(date);
        LocalDateTime startDateTime = quarterStart.atStartOfDay();
        LocalDateTime endDateTime = quarterEnd.atStartOfDay();
        return repository.findAllByTinAndDate(tin, startDateTime, endDateTime);
    }

    public LocalDate getQuarterStart(LocalDate date) {
        int quarter = (date.getMonthValue() - 1) / 3 + 1;
        return LocalDate.of(date.getYear(), (quarter - 1) * 3 + 1, 1);
    }

    public LocalDate getQuarterEnd(LocalDate date) {
        return getQuarterStart(date).plusMonths(3).minusDays(1);
    }

    @Scheduled(cron = "0 0 9 31 3 *")  // 31-mart 10:00 da
    @Scheduled(cron = "0 0 9 30 6 *")  // 30-iyun 10:00 da
    @Scheduled(cron = "0 0 9 30 9 *")  // 30-sentyabr 10:00 da
    @Scheduled(cron = "0 0 9 31 12 *") // 31-dekabr 10:00 da
    public void sumScore() {
        LocalDate date = LocalDate.now();
        LocalDate quarterStart = getQuarterStart(date);
        LocalDate quarterEnd = getQuarterEnd(date);
        LocalDateTime startDateTime = quarterStart.atStartOfDay();
        LocalDateTime endDateTime = quarterEnd.atStartOfDay();
        List<HFRAssessmentDto> allGroupByHazardousFacilityAndTin = repository.findAllGroupByHazardousFacilityAndTin(startDateTime, endDateTime);
        // Barcha qiymatlarni guruhlash: TIN + hazardousFacilityId
        Map<Short, List<HFRAssessmentDto>> groupedByTin = allGroupByHazardousFacilityAndTin.stream()
                .collect(Collectors.groupingBy(HFRAssessmentDto::tin));

// Har bir TIN uchun hisoblash
        for (Map.Entry<Short, List<HFRAssessmentDto>> entry : groupedByTin.entrySet()) {
            Short tin = entry.getKey();
            List<HFRAssessmentDto> dtoList = entry.getValue();

            // Null bo'lgan va bo'lmaganlarni ajratib olish
            Optional<HFRAssessmentDto> nullFacility = dtoList.stream()
                    .filter(dto -> dto.hazardousFacilityId() == null)
                    .findFirst();

            int nullScore = nullFacility.map(HFRAssessmentDto::sumScore).orElse(0);

            // Endi null bo'lmaganlarga qo‘shib saqlaymiz
            dtoList.stream()
                    .filter(dto -> dto.hazardousFacilityId() != null)
                    .forEach(dto -> {
                        riskAssessmentRepository.save(
                                HazardousFacilityRiskAssessment.builder()
                                        .sumScore(dto.sumScore() + nullScore)
                                        .objectName(
                                                hazardousFacilityRepository.findById(dto.hazardousFacilityId())
                                                        .map(HazardousFacility::getName)
                                                        .orElse("Nomi ma'lum emas.")

                                        )
                                        .tin(tin)
                                        .build()
                        );
                    });
        }

    }

}
