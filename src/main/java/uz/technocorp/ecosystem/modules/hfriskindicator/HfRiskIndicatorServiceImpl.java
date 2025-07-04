package uz.technocorp.ecosystem.modules.hfriskindicator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityRepository;
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.FilePathDto;
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
import uz.technocorp.ecosystem.modules.hfriskindicator.dto.HFRIndicatorDto;
import uz.technocorp.ecosystem.modules.riskassessment.enums.RiskAssessmentIndicator;
import uz.technocorp.ecosystem.modules.hfriskindicator.view.RiskIndicatorView;

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
public class HfRiskIndicatorServiceImpl implements HfRiskIndicatorService {

    private final HfRiskIndicatorRepository repository;
    private final HazardousFacilityRepository hazardousFacilityRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;
    private final RiskAnalysisIntervalRepository intervalRepository;
    private final InspectionRepository inspectionRepository;
    private final ProfileRepository profileRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public void create(List<HFRIndicatorDto> dtoList) {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));
//        List<HfRiskIndicator> allByQuarter = repository.findAllByQuarter(riskAnalysisInterval.getId(), dto.hazardousFacilityId());
//        if (allByQuarter.isEmpty()) {
//            if (!dto.indicatorType().equals(RiskAssessmentIndicator.PARAGRAPH_HF_1)) {
//                HazardousFacility hazardousFacility = hazardousFacilityRepository
//                        .findById(dto.hazardousFacilityId())
//                        .orElseThrow(() -> new ResourceNotFoundException("XICHO", "Id", dto.hazardousFacilityId()));
////                String identificationCardPath = hazardousFacility.getIdentificationCardPath();
////                String expertOpinionPath = hazardousFacility.getExpertOpinionPath();
////                String industrialSafetyDeclarationPath = hazardousFacility.getIndustrialSafetyDeclarationPath();
////                String insurancePolicyPath = hazardousFacility.getInsurancePolicyPath();
//
//                String identificationCardPath = hazardousFacility.getFiles().get("identificationCardPath");
//                String expertOpinionPath = hazardousFacility.getFiles().get("expertOpinionPath");
//                String industrialSafetyDeclarationPath = hazardousFacility.getFiles().get("industrialSafetyDeclarationPath");
//                String insurancePolicyPath = hazardousFacility.getFiles().get("insurancePolicyPath");
//
//
//                StringBuilder descriptionBuilder = new StringBuilder();
//                if (identificationCardPath.isEmpty()) {
//                    descriptionBuilder.append("Identifikatsiya xulosasi mavjud emas. ");
//                }
//                if (expertOpinionPath.isEmpty()) {
//                    descriptionBuilder.append("Ekspertiza xulosasi mavjud emas. ");
//                }
//                if (industrialSafetyDeclarationPath.isEmpty()) {
//                    descriptionBuilder.append("Sanoat xavfsizligi deklaratsiyasi mavjud emas. ");
//                }
//                if (insurancePolicyPath.isEmpty()) {
//                    descriptionBuilder.append("Majburiy sug'urta polisi mavjud emas. ");
//                }
//                repository.save(
//                        HfRiskIndicator
//                                .builder()
//                                .hazardousFacilityId(dto.hazardousFacilityId())
//                                .indicatorType(RiskAssessmentIndicator.PARAGRAPH_HF_1)
//                                .score(RiskAssessmentIndicator.PARAGRAPH_HF_1.getScore())
//                                .description(descriptionBuilder.toString())
//                                .tin(dto.tin())
//                                .riskAnalysisInterval(riskAnalysisInterval)
//                                .build()
//                );
//            }
//        }
        for (HFRIndicatorDto dto : dtoList) {
            repository.save(
                    HfRiskIndicator
                            .builder()
                            .hazardousFacilityId(dto.hazardousFacilityId())
                            .indicatorType(dto.indicatorType())
                            .score(dto.indicatorType().getScore())
                            .description(dto.description())
                            .tin(dto.tin())
                            .scoreValue(dto.indicatorType().getScore())
                            .riskAnalysisInterval(riskAnalysisInterval)
                            .build()
            );
        }
//            HfRiskIndicator existRiskIndicator = allByQuarter.stream().filter(riskIndicator -> riskIndicator.getIndicatorType().equals(dto.indicatorType())).toList().getFirst();
//            if (existRiskIndicator != null) {
//                throw new RuntimeException("Ushbu ko'rsatkich bo'yicha ma'lumot kiritilgan!");
//            }

//        }
    }

    @Override
    public void update(UUID id, HFRIndicatorDto dto) {
        HfRiskIndicator riskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasini baholash ko'rsatkichi", "Id", id));
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
        HfRiskIndicator hfRiskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasini baholash ko'rsatkichi", "id", id));
        hfRiskIndicator.setFilePath(attachment.getPath());
        hfRiskIndicator.setFileDate(LocalDate.now());
        repository.save(hfRiskIndicator);
        attachmentRepository.deleteById(attachment.getId());
    }

    @Override
    public void cancelRiskIndicator(UUID id) {
        HfRiskIndicator hfRiskIndicator = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xavf darajasini baholash ko'rsatkichi", "id", id));
        hfRiskIndicator.setScore(0);
        hfRiskIndicator.setCancelledDate(LocalDate.now());
        repository.save(hfRiskIndicator);
    }

    @Override
    public List<RiskIndicatorView> findAllByHFIdAndTin(UUID id, Long tin, Integer intervalId) {
        return repository.findAllByHfIdAndTinAndDate(id, tin, intervalId);
    }

    @Override
    public List<RiskIndicatorView> findAllByTin(Long tin, Integer intervalId) {
        return repository.findAllByTinAndDate(tin, intervalId);
    }

    @Override
    public List<RiskIndicatorView> findAllToFixByTin(Long tin, UUID id, Integer intervalId) {
        return repository.findAllFileContainsByTinAndDate(tin, id, intervalId);
    }

    @Scheduled(cron = "0 0 22 31 3 *")  // 31-mart 22:00 da
    @Scheduled(cron = "0 0 22 30 6 *")  // 30-iyun 22:00 da
    @Scheduled(cron = "0 0 22 30 9 *")  // 30-sentyabr 22:00 da
    @Scheduled(cron = "0 0 22 31 12 *") // 31-dekabr 22:00 da
    public void sumScore() {
        RiskAnalysisInterval riskAnalysisInterval = intervalRepository
                .findByStatus(RiskAnalysisIntervalStatus.CURRENT)
                .orElseThrow(() -> new ResourceNotFoundException("Oraliq", "qiymat", RiskAnalysisIntervalStatus.CURRENT));

        List<RiskAssessmentDto> allGroupByHazardousFacilityAndTin = repository.findAllGroupByHfIdAndTin(riskAnalysisInterval.getId());
        // Barcha qiymatlarni guruhlash: TIN + hazardousFacilityId
        Map<Long, List<RiskAssessmentDto>> groupedByTin = allGroupByHazardousFacilityAndTin.stream()
                .collect(Collectors.groupingBy(RiskAssessmentDto::getTin));

        // Har bir TIN uchun hisoblash
        for (Map.Entry<Long, List<RiskAssessmentDto>> entry : groupedByTin.entrySet()) {
            Long tin = entry.getKey();
            List<RiskAssessmentDto> dtoList = entry.getValue();

            // Null bo'lgan va bo'lmaganlarni ajratib olish
            Optional<RiskAssessmentDto> nullFacility = dtoList.stream()
                    .filter(dto -> dto.getObjectId() == null)
                    .findFirst();

            int organizationScore = nullFacility.map(RiskAssessmentDto::getSumScore).orElse(0);


            // Endi null bo'lmaganlarga qo‘shib saqlaymiz
            dtoList.stream()
                    .filter(dto -> dto.getObjectId() != null)
                    .forEach(dto -> {
                        riskAssessmentRepository.save(
                                RiskAssessment.builder()
                                        .sumScore(dto.getSumScore() + organizationScore)
                                        .tin(tin)
                                        .hazardousFacilityId(dto.getObjectId())
                                        .riskAnalysisInterval(riskAnalysisInterval)
                                        .build()
                        );
                        if (dto.getSumScore() + organizationScore > 80) {
                            Optional<Inspection> inspectionOptional = inspectionRepository
                                    .findAllByTinAndIntervalId(tin, riskAnalysisInterval.getId());
                            Set<Integer> regionIds = hazardousFacilityRepository.getAllRegionIdByLegalTin(tin);
                            if (inspectionOptional.isEmpty()) {
                                profileRepository
                                        .findByTin(tin)
                                        .ifPresent(profile -> {
                                            inspectionRepository.save(
                                                    Inspection
                                                            .builder()
                                                            .tin(dto.getTin())
                                                            .profileId(profile.getId())
                                                            .regionId(profile.getRegionId())
                                                            .regionIds(regionIds)
                                                            .districtId(profile.getDistrictId())
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
