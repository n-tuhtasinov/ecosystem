package uz.technocorp.ecosystem.modules.irsriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.FilePathDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.inspection.Inspection;
import uz.technocorp.ecosystem.modules.inspection.InspectionRepository;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
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

import java.time.LocalDate;
import java.util.*;
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
    private final AttachmentRepository attachmentRepository;

    @Override
    public void create(List<IrsRiskIndicatorDto> dtos) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

//        List<IrsRiskIndicator> allByQuarter = repository.findAllByQuarter(dto.intervalId(), dto.irsId());
//
//        IrsRiskIndicator existRiskIndicator = allByQuarter.stream().filter(riskIndicator -> riskIndicator.getIndicatorType().equals(dto.indicatorType())).toList().getFirst();
//        if (existRiskIndicator != null) {
//            throw new RuntimeException("Ushbu ko'rsatkich bo'yicha ma'lumot kiritilgan!");
//        }
        for (IrsRiskIndicatorDto dto : dtos) {
            repository.save(
                    IrsRiskIndicator
                            .builder()
                            .ionizingRadiationSourceId(dto.irsId())
                            .indicatorType(dto.indicatorType())
                            .score(dto.indicatorType().getScore())
                            .description(dto.description())
                            .tin(dto.tin())
                            .riskAnalysisInterval(riskAnalysisInterval)
                            .build()
            );
        }

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
    public void attachFile(UUID id, FilePathDto dto) {
        Attachment attachment = attachmentRepository
                .findByPath(dto.path())
                .orElseThrow(() -> new ResourceNotFoundException("File", "url", dto.path()));
        IrsRiskIndicator riskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasini baholash ko'rsatkichi", "id", id));
        riskIndicator.setFilePath(attachment.getPath());
        riskIndicator.setFileDate(LocalDate.now());
        repository.save(riskIndicator);
        attachmentRepository.deleteById(attachment.getId());
    }

    @Override
    public void cancelRiskIndicator(UUID id) {
        IrsRiskIndicator riskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasini baholash ko'rsatkichi", "id", id));
        riskIndicator.setScore(0);
        riskIndicator.setCancelledDate(LocalDate.now());
        repository.save(riskIndicator);
    }

    @Override
    public List<RiskIndicatorView> findAllByIrsIdAndTin(UUID id, Long tin, Integer intervalId) {
        return repository.findAllByIrsIdAndTinAndDate(id, tin, intervalId);
    }

    @Override
    public List<RiskIndicatorView> findAllByTin(Long tin, Integer intervalId) {
        return repository.findAllByTinAndDate(tin, intervalId);
    }

    @Override
    public Page<RiskIndicatorView> findAllToFixByTin(Long tin, Integer intervalId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return repository.findAllFileContainsByTinAndDate(tin, intervalId, pageable);
    }

    @Scheduled(cron = "0 0 22 31 3 *")  // 31-mart 10:00 da
    @Scheduled(cron = "0 0 22 30 6 *")  // 30-iyun 10:00 da
    @Scheduled(cron = "0 0 22 30 9 *")  // 30-sentyabr 10:00 da
    @Scheduled(cron = "0 0 22 31 12 *") // 31-dekabr 10:00 da
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
//                                        .objectName(
//                                                irsRepository.findById(dto.objectId())
//                                                        .map(IonizingRadiationSource::getSymbol)
//                                                        .orElse("Nomi ma'lum emas.")
//
//                                        )
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
                                        .ifPresent(profile -> inspectionRepository.save(
                                                Inspection
                                                        .builder()
                                                        .tin(dto.tin())
                                                        .regionId(profile.getRegionId())
                                                        .districtId(profile.getDistrictId())
                                                        .regionIds(regionIds)
                                                        .status(InspectionStatus.NEW)
                                                        .build()
                                        ));

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
