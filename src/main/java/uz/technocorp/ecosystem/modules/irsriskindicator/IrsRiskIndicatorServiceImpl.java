package uz.technocorp.ecosystem.modules.irsriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.inspection.Inspection;
import uz.technocorp.ecosystem.modules.inspection.InspectionRepository;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSource;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSourceRepository;
import uz.technocorp.ecosystem.modules.irsriskindicator.dto.IrsRiskIndicatorDto;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisIntervalRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;
import uz.technocorp.ecosystem.modules.riskassessment.RiskAssessment;
import uz.technocorp.ecosystem.modules.riskassessment.RiskAssessmentRepository;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 16.04.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class IrsRiskIndicatorServiceImpl implements IrsRiskIndicatorService {

    private final IrsRiskIndicatorRepository repository;
    private final IonizingRadiationSourceRepository irsRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskAnalysisIntervalRepository intervalRepository;
    private final InspectionRepository inspectionRepository;
    private final ProfileRepository profileRepository;

    @Override
    public void create(IrsRiskIndicatorDto dto) {
        List<IrsRiskIndicator> allByQuarter = repository.findAllByQuarter(dto.intervalId(), dto.irsId());

        IrsRiskIndicator existRiskIndicator = allByQuarter.stream().filter(riskIndicator -> riskIndicator.getIndicatorType().equals(dto.indicatorType())).toList().getFirst();
        if (existRiskIndicator != null) {
            throw new RuntimeException("Ushbu ko'rsatkich bo'yicha ma'lumot kiritilgan!");
        }
        repository.save(
                IrsRiskIndicator
                        .builder()
                        .ionizingRadiationSourceId(dto.irsId())
                        .indicatorType(dto.indicatorType())
                        .score(dto.indicatorType().getScore())
                        .description(dto.description())
                        .tin(dto.tin())
                        .build()
        );
    }

    @Override
    public void update(UUID id, IrsRiskIndicatorDto dto) {
        IrsRiskIndicator irsRiskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasi", "Id", id));
        irsRiskIndicator.setDescription(dto.description());
        repository.save(irsRiskIndicator);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<RiskIndicatorView> findAllByIrsIdAndTin(UUID id, Long tin) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

        return repository.findAllByIrsIdAndTinAndDate(id, tin, riskAnalysisInterval.getId());
    }

    @Override
    public List<RiskIndicatorView> findAllByTin(Long tin) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

        return repository.findAllByTinAndDate(tin, riskAnalysisInterval.getId());
    }

    @Scheduled(cron = "0 0 9 31 3 *")  // 31-mart 10:00 da
    @Scheduled(cron = "0 0 9 30 6 *")  // 30-iyun 10:00 da
    @Scheduled(cron = "0 0 9 30 9 *")  // 30-sentyabr 10:00 da
    @Scheduled(cron = "0 0 9 31 12 *") // 31-dekabr 10:00 da
    public void sumScore() {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

        List<RiskAssessmentDto> allGroupByIrsAndTin = repository.findAllGroupByIrsAndTin(riskAnalysisInterval.getId());
        // Barcha qiymatlarni guruhlash: TIN + IrsId
        Map<Long, List<RiskAssessmentDto>> groupedByTin = allGroupByIrsAndTin.stream()
                .collect(Collectors.groupingBy(RiskAssessmentDto::tin));

        // Har bir TIN uchun hisoblash
        for (Map.Entry<Long, List<RiskAssessmentDto>> entry : groupedByTin.entrySet()) {
            Long tin = entry.getKey();
            List<RiskAssessmentDto> dtoList = entry.getValue();

            // Null bo'lgan va bo'lmaganlarni ajratib olish
            Optional<RiskAssessmentDto> nullIrs = dtoList.stream()
                    .filter(dto -> dto.objectId() == null)
                    .findFirst();

            int organizationScore = nullIrs.map(RiskAssessmentDto::sumScore).orElse(0);

            // Endi null bo'lmaganlarga qo‘shib saqlaymiz
            dtoList.stream()
                    .filter(dto -> dto.objectId() != null)
                    .forEach(dto -> {

                        riskAssessmentRepository.save(
                                RiskAssessment.builder()
                                        .sumScore(dto.sumScore() + organizationScore)
                                        .objectName(
                                                irsRepository.findById(dto.objectId())
                                                        .map(IonizingRadiationSource::getSymbol)
                                                        .orElse("Nomi ma'lum emas.")

                                        )
                                        .tin(tin)
                                        .ionizingRadiationSourceId(dto.objectId())
                                        .riskAnalysisInterval(riskAnalysisInterval)
                                        .build()
                        );


                        if (dto.sumScore() + organizationScore > 80) {
                            Set<Integer> regionIds = irsRepository.getAllRegionIdByLegalTin(tin);
                            Optional<Inspection> inspectionOptional = inspectionRepository
                                    .findAllByTinAndIntervalId(tin, riskAnalysisInterval.getId());
                            if (inspectionOptional.isEmpty()) {
                                profileRepository
                                        .findByTin(tin)
                                        .ifPresent(profile -> {
                                            inspectionRepository.save(
                                                    Inspection
                                                            .builder()
                                                            .tin(dto.tin())
                                                            .regionId(profile.getRegionId())
                                                            .districtId(profile.getDistrictId())
                                                            .build()
                                            );
                                        });

                            } else {
                                Inspection inspection = inspectionOptional.get();
                                Set<Integer> existRegionIds = inspection.getRegionIds();
                                existRegionIds.addAll(regionIds);
                                inspection.setRegionIds(existRegionIds);
                                inspectionRepository.save(inspection);
                            }
                        }
                    });
        }

    }

}
