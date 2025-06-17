package uz.technocorp.ecosystem.modules.riskanalysisinterval;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 25.04.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class RiskAnalysisIntervalServiceImpl implements RiskAnalysisIntervalService {

    private final RiskAnalysisIntervalRepository repository;

    @Override
    public List<RiskAnalysisInterval> findAll(Integer year) {
        if (year == null) return repository.findAll();
        return repository.findAllByYear(year);
    }

    @Override
    public RiskAnalysisInterval findByStatus() {
        return repository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Xavfni baholash oralig'i", "qiymat", RiskAnalysisIntervalStatus.CURRENT));
    }

    @Scheduled(cron = "0 0 5 1 1 *")  // 1-yanvar 05:00 da
    @Scheduled(cron = "0 0 5 1 4 *")  // 1-aprel 05:00 da
    @Scheduled(cron = "0 0 5 1 7 *")  // 1-iyul 05:00 da
    @Scheduled(cron = "0 0 5 1 10 *") // 1-oktyabr 05:00 da
    public void createRiskAnalysisInterval() {

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        RiskAnalysisInterval newInterval = RiskAnalysisInterval
                .builder()
                .year(year)
                .status(RiskAnalysisIntervalStatus.CURRENT)
                .build();
        if (month <= 3) {
            newInterval.setStartDate(LocalDate.of(year, 1, 1));
            newInterval.setEndDate(LocalDate.of(year, 3, 31));
        } else if (month <= 6) {
            newInterval.setStartDate(LocalDate.of(year, 4, 1));
            newInterval.setEndDate(LocalDate.of(year, 6, 30));
        } else if (month <= 9) {
            newInterval.setStartDate(LocalDate.of(year, 7, 1));
            newInterval.setEndDate(LocalDate.of(year, 9, 30));
        } else {
            newInterval.setStartDate(LocalDate.of(year, 10, 1));
            newInterval.setEndDate(LocalDate.of(year, 12, 31));
        }
        RiskAnalysisInterval riskAnalysisInterval = repository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElse(null);
        if (riskAnalysisInterval != null) {
            riskAnalysisInterval.setStatus(RiskAnalysisIntervalStatus.PAST);
            repository.save(riskAnalysisInterval);
        }
        repository.save(newInterval);
    }
}
