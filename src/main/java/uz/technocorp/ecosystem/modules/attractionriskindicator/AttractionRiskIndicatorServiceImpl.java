package uz.technocorp.ecosystem.modules.attractionriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.elevatorriskindicator.dto.EquipmentRiskIndicatorDto;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentRepository;
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.FilePathDto;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;
import uz.technocorp.ecosystem.modules.inspection.Inspection;
import uz.technocorp.ecosystem.modules.inspection.InspectionRepository;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSource;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisInterval;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.RiskAnalysisIntervalRepository;
import uz.technocorp.ecosystem.modules.riskanalysisinterval.enums.RiskAnalysisIntervalStatus;
import uz.technocorp.ecosystem.modules.riskassessment.RiskAssessment;
import uz.technocorp.ecosystem.modules.riskassessment.RiskAssessmentRepository;
import uz.technocorp.ecosystem.modules.riskassessment.dto.RiskAssessmentDto;

import java.time.LocalDate;
import java.util.*;
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
public class AttractionRiskIndicatorServiceImpl implements AttractionRiskIndicatorService {

    private final AttractionRiskIndicatorRepository repository;
    private final EquipmentRepository equipmentRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskAnalysisIntervalRepository intervalRepository;
    private final InspectionRepository inspectionRepository;
    private final ProfileRepository profileRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public void create(List<EquipmentRiskIndicatorDto> dtos) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));
        for (EquipmentRiskIndicatorDto dto : dtos) {
//            List<AttractionRiskIndicator> allByQuarter = repository.findAllByQuarter(riskAnalysisInterval.getId(), dto.equipmentId());
//            if (!allByQuarter.isEmpty()) {
//                AttractionRiskIndicator existRiskIndicator = allByQuarter
//                        .stream()
//                        .filter(riskIndicator -> riskIndicator
//                                .getIndicatorType()
//                                .equals(dto.indicatorType()))
//                        .toList()
//                        .getFirst();
//                if (existRiskIndicator != null) {
//                    throw new RuntimeException("Ushbu ko'rsatkich bo'yicha ma'lumot kiritilgan!");
//                }
//
//            }
            repository.save(
                    AttractionRiskIndicator
                            .builder()
                            .equipmentId(dto.equipmentId())
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
    public void attachFile(UUID id, FilePathDto dto) {
        Attachment attachment = attachmentRepository
                .findByPath(dto.path())
                .orElseThrow(() -> new ResourceNotFoundException("File", "url", dto.path()));
        AttractionRiskIndicator riskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasini baholash ko'rsatkichi", "id", id));
        riskIndicator.setFilePath(attachment.getPath());
        riskIndicator.setFileDate(LocalDate.now());
        repository.save(riskIndicator);
        attachmentRepository.deleteById(attachment.getId());
    }

    @Override
    public void cancelRiskIndicator(UUID id) {
        AttractionRiskIndicator riskIndicator = repository
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
        return repository.findAllByTinAndDate(tin, intervalId);
    }

    @Override
    public Page<RiskIndicatorView> findAllToFixByTin(Long tin, Integer intervalId, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
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

        List<RiskAssessmentDto> allGroupByEquipmentAndTin = repository.findAllGroupByEquipmentAndTin(riskAnalysisInterval.getId());
        // Barcha qiymatlarni guruhlash: TIN + EquipmentId
        Map<Long, List<RiskAssessmentDto>> groupedByTin = allGroupByEquipmentAndTin.stream()
                .collect(Collectors.groupingBy(RiskAssessmentDto::tin));

        // Har bir TIN uchun hisoblash
        for (Map.Entry<Long, List<RiskAssessmentDto>> entry : groupedByTin.entrySet()) {
            Long tin = entry.getKey();
            List<RiskAssessmentDto> dtoList = entry.getValue();

            // Null bo'lgan va bo'lmaganlarni ajratib olish
            Optional<RiskAssessmentDto> nullEquipment = dtoList.stream()
                    .filter(dto -> dto.objectId() == null)
                    .findFirst();

            int organizationScore = nullEquipment.map(RiskAssessmentDto::sumScore).orElse(0);

            // Endi null bo'lmaganlarga qo‘shib saqlaymiz
            dtoList.stream()
                    .filter(dto -> dto.objectId() != null)
                    .forEach(dto -> {
                        riskAssessmentRepository.save(
                                RiskAssessment.builder()
                                        .sumScore(dto.sumScore() + organizationScore)
//                                        .objectName(
//                                                equipmentRepository.findById(dto.objectId())
//                                                        .map(Equipment::getRegistryNumber)
//                                                        .orElse("Nomi ma'lum emas.")
//
//                                        )
                                        .tin(tin)
                                        .equipmentId(dto.objectId())
                                        .riskAnalysisInterval(riskAnalysisInterval)
                                        .build()
                        );
                        if (dto.sumScore() + organizationScore > 80) {
                            Optional<Inspection> inspectionOptional = inspectionRepository
                                    .findAllByTinAndIntervalId(tin, riskAnalysisInterval.getId());
                            Set<Integer> regionIds = equipmentRepository.getAllRegionIdByLegalTin(tin);
                            if (inspectionOptional.isPresent()) {
                                Inspection inspection = inspectionOptional.get();
                                Set<Integer> existRegionIds = inspection.getRegionIds();
                                existRegionIds.addAll(regionIds);
                                inspection.setRegionIds(existRegionIds);
                                inspectionRepository.save(inspection);
                            }  else {
                                profileRepository
                                        .findByTin(tin)
                                        .ifPresent(profile -> {
                                            inspectionRepository.save(
                                                    Inspection
                                                            .builder()
                                                            .tin(dto.tin())
                                                            .regionId(profile.getRegionId())
                                                            .regionIds(regionIds)
                                                            .districtId(profile.getDistrictId())
                                                            .build()
                                            );
                                });
                            }
                        }
                    });
        }

    }

}
