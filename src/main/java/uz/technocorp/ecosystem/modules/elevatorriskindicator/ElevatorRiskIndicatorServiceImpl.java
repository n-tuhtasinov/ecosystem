package uz.technocorp.ecosystem.modules.elevatorriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.elevatorriskindicator.dto.EquipmentRiskIndicatorDto;
import uz.technocorp.ecosystem.modules.equipment.EquipmentRepository;
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.FilePathDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.inspection.Inspection;
import uz.technocorp.ecosystem.modules.inspection.InspectionRepository;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
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
public class ElevatorRiskIndicatorServiceImpl implements ElevatorRiskIndicatorService {

    private final ElevatorRiskIndicatorRepository repository;
    private final EquipmentRepository equipmentRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskAnalysisIntervalRepository intervalRepository;
    private final InspectionRepository inspectionRepository;
    private final ProfileRepository profileRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public void create(List<EquipmentRiskIndicatorDto> dtoList) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));
//        List<ElevatorRiskIndicator> allByQuarter = repository.findAllByQuarter(riskAnalysisInterval.getId(), dto.equipmentId());
//        if (!allByQuarter.isEmpty()) {
//            ElevatorRiskIndicator existRiskIndicator = allByQuarter
//                    .stream()
//                    .filter(riskIndicator -> riskIndicator
//                            .getIndicatorType()
//                            .equals(dto.indicatorType()))
//                    .toList()
//                    .getFirst();
//            if (existRiskIndicator != null) {
//                throw new RuntimeException("Ushbu ko'rsatkich bo'yicha ma'lumot kiritilgan!");
//            }
//
//        }
        for (EquipmentRiskIndicatorDto dto : dtoList) {
            repository.save(
                    ElevatorRiskIndicator
                            .builder()
                            .equipmentId(dto.equipmentId())
                            .indicatorType(dto.indicatorType())
                            .score(dto.indicatorType().getScore())
                            .description(dto.description())
                            .tin(dto.tin())
                            .scoreValue(dto.indicatorType().getScore())
                            .riskAnalysisInterval(riskAnalysisInterval)
                            .build()
            );
        }

    }

    @Override
    public void update(UUID id, EquipmentRiskIndicatorDto dto) {
        ElevatorRiskIndicator riskIndicator = repository
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
    public void attachFile(UUID id, FilePathDto dto) {
        Attachment attachment = attachmentRepository
                .findByPath(dto.path())
                .orElseThrow(() -> new ResourceNotFoundException("File", "url", dto.path()));
        ElevatorRiskIndicator riskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasini baholash ko'rsatkichi", "id", id));
        riskIndicator.setFilePath(attachment.getPath());
        riskIndicator.setFileDate(LocalDate.now());
        repository.save(riskIndicator);
        attachmentRepository.deleteById(attachment.getId());
    }

    @Override
    public void cancelRiskIndicator(UUID id) {
        ElevatorRiskIndicator riskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasini baholash ko'rsatkichi", "id", id));
        riskIndicator.setScore(0);
        riskIndicator.setCancelledDate(LocalDate.now());
        repository.save(riskIndicator);
    }

    @Override
    public List<RiskIndicatorView> findAllByEquipmentIdAndTin(UUID id, Long tin, Integer intervalId) {
        return repository.findAllByEquipmentIdAndTinAndDate(id, tin, intervalId);
    }

    @Override
    public List<RiskIndicatorView> findAllByTin(Long tin, Integer intervalId) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));
        return repository.findAllByTinAndDate(tin, riskAnalysisInterval.getId());
    }

    @Override
    public List<RiskIndicatorView> findAllToFixByTin(Long tin, UUID id, Integer intervalId) {
        return repository.findAllFileContainsByTinAndDate(tin, intervalId, id);
    }

    @Scheduled(cron = "0 30 23 31 3 *")  // 31-mart 23:30 da
    @Scheduled(cron = "0 30 23 30 6 *")  // 30-iyun 23:30 da
    @Scheduled(cron = "0 30 23 30 9 *")  // 30-sentyabr 23:30 da
    @Scheduled(cron = "0 30 23 31 12 *") // 31-dekabr 23:30 da
    public void sumScore() {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

        List<RiskAssessmentDto> allGroupByEquipmentAndTin = repository.findAllGroupByEquipmentAndTin(riskAnalysisInterval.getId());
        // Barcha qiymatlarni guruhlash: TIN + EquipmentId
        Map<Long, List<RiskAssessmentDto>> groupedByTin = allGroupByEquipmentAndTin.stream()
                .collect(Collectors.groupingBy(RiskAssessmentDto::getTin));

        // Har bir TIN uchun hisoblash
        for (Map.Entry<Long, List<RiskAssessmentDto>> entry : groupedByTin.entrySet()) {
            Long tin = entry.getKey();
            List<RiskAssessmentDto> dtoList = entry.getValue();

            // Null bo'lgan va bo'lmaganlarni ajratib olish
            Optional<RiskAssessmentDto> nullEquipment = dtoList.stream()
                    .filter(dto -> dto.getObjectId() == null)
                    .findFirst();

            int organizationScore = nullEquipment.map(RiskAssessmentDto::getSumScore).orElse(0);

            // Endi null bo'lmaganlarga qo‘shib saqlaymiz
            dtoList.stream()
                    .filter(dto -> dto.getObjectId() != null)
                    .forEach(dto -> {
                        riskAssessmentRepository.save(
                                RiskAssessment.builder()
                                        .sumScore(dto.getSumScore() + organizationScore)
                                        .tin(tin)
                                        .equipmentId(dto.getObjectId())
                                        .riskAnalysisInterval(riskAnalysisInterval)
                                        .build()
                        );
                        if (dto.getSumScore() + organizationScore > 80) {
                            Optional<Inspection> inspectionOptional = inspectionRepository
                                    .findAllByTinAndIntervalId(tin, riskAnalysisInterval.getId());
                            Set<Integer> regionIds = equipmentRepository.getAllRegionIdByLegalTin(tin);
                            if (inspectionOptional.isEmpty()) {
                                profileRepository.findByTin(tin).ifPresent(profile -> {
                                    inspectionRepository.save(
                                            Inspection
                                                    .builder()
                                                    .tin(dto.getTin())
                                                    .regionId(profile.getRegionId())
                                                    .districtId(profile.getDistrictId())
                                                    .regionIds(regionIds)
                                                    .status(InspectionStatus.NEW)
                                                    .intervalId(riskAnalysisInterval.getId())
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
