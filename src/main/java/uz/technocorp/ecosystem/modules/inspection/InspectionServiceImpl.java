package uz.technocorp.ecosystem.modules.inspection;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.SignedReplyDto;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.document.DocumentService;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionActDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionUpdateDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectorShortInfo;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionFullDto;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionShortInfo;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionView;
import uz.technocorp.ecosystem.modules.inspection.view.InspectorInfoForInspectionAct;
import uz.technocorp.ecosystem.modules.inspectionreport.InspectionReportService;
import uz.technocorp.ecosystem.modules.inspectionreport.view.InspectionReportForAct;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.template.Template;
import uz.technocorp.ecosystem.modules.template.TemplateService;
import uz.technocorp.ecosystem.modules.template.TemplateType;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class InspectionServiceImpl implements InspectionService {

    private final InspectionRepository repository;
    private final ObjectMapper objectMapper;
    private final DocumentService documentService;
    private final InspectionReportService inspectionReportService;
    private final TemplateService templateService;
    private final ProfileService profileService;
    private final OfficeService officeService;
    private final AttachmentService attachmentService;


    @Override
    public void update(UUID id, InspectionDto dto) {
        Inspection inspection = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        inspection.setTin(dto.tin());
        inspection.setEndDate(dto.endDate());
        inspection.setStartDate(dto.startDate());
        inspection.setDecreePath(dto.decreePath());
        inspection.setInspectorIds(dto.inspectorIdList());
        inspection.setIntervalId(dto.intervalId());
        inspection.setStatus(InspectionStatus.IN_PROCESS);
        inspection.setDecreeDate(dto.decreeDate());
        inspection.setDecreeNumber(dto.decreeNumber());
        repository.save(inspection);
    }

    @Override
    public void update(UUID id, InspectionUpdateDto dto) {
        Inspection inspection = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        inspection.setMeasuresPath(dto.measuresPath());
        inspection.setNotificationLetterDate(dto.notificationLetterDate());
        inspection.setNotificationLetterPath(dto.notificationLetterPath());
        inspection.setSchedulePath(dto.schedulePath());
        inspection.setSpecialCode(dto.specialCode());
        inspection.setProgramPath(dto.programPath());
        inspection.setResultPath(dto.resultPath());
        inspection.setOrderPath(dto.orderPath());
        repository.save(inspection);
    }

    @Override
    public void updateStatus(UUID id, InspectionStatus status) {
        Inspection inspection = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        if (status.equals(InspectionStatus.COMPLETED)) {
            if (inspection.getStatus().equals(InspectionStatus.UNRESOLVED)) {
                inspection.setStatus(InspectionStatus.DELAYED);
            }
        } else {
            inspection.setStatus(status);
        }
        repository.save(inspection);
    }

    @Override
    public Page<InspectionCustom> getAll(User user, int page, int size, Long tin, InspectionStatus status, Integer intervalId) {
        return repository.getInspectionCustoms(user, page, size, tin, status, intervalId);
    }

    @Override
    public InspectionFullDto getById(UUID id) {
        InspectionView view = repository
                .getInspectionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tekshiruv", "Id", id));
        List<InspectorShortInfo> inspectors;
        try {
            inspectors = objectMapper.readValue(
                    view.getInspectors(),
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse inspectors JSON", e);
        }


        return new InspectionFullDto(
                view.getId(),
                view.getStartDate(),
                view.getEndDate(),
                view.getStatus(),
                view.getSpecialCode(),
                view.getDecreePath(),
                view.getSchedulePath(),
                view.getNotificationLetterPath(),
                view.getNotificationLetterDate(),
                view.getOrderPath(),
                view.getProgramPath(),
                view.getMeasuresPath(),
                view.getResultPath(),
                inspectors
        );
    }

    @Override
    public List<InspectionShortInfo> getAllByInspector(User user, LocalDate startDate, LocalDate endDate) {
        return repository.getAllByInspectorId(user.getId(), startDate, endDate, InspectionStatus.IN_PROCESS.name());
    }

    @Transactional
    @Override
    public void createAct(User user, SignedReplyDto<InspectionActDto> actDto, HttpServletRequest request) {
        documentService.create(
                new DocumentDto(
                        actDto.getDto().getInspectionId(),
                        DocumentType.ACT,
                        actDto.getFilePath(),
                        actDto.getSign(),
                        Helper.getIp(request),
                        user.getId(),
                        List.of(user.getId()),
                        null)

        );
        updateStatus(user.getId(), InspectionStatus.CONDUCTED);
    }

    @Override
    public String generatePdf(User user, InspectionActDto dto, HttpServletRequest request) {
        // User
        Profile profile = profileService.getProfile(user.getProfileId());
        Office userOffice = officeService.findById(profile.getOfficeId());

        // Inspection
        Inspection inspection = repository.findById(dto.getInspectionId()).orElseThrow(() -> new ResourceNotFoundException("Tekshiruv topilmadi"));
        Profile inspectionProfile = profileService.findByTin(inspection.getTin());
        Office inspectionOffice = officeService.findByRegionId(inspection.getRegionId());
        String[] splitDate = inspection.getDecreeDate().format((DateTimeFormatter.ofPattern("yyyy MMMM dd", Locale.of("uz")))).split(" ");
        String date = splitDate[0] + " yil " + splitDate[2] + " " + splitDate[1];

        // Inspectors
        List<InspectorInfoForInspectionAct> inspectors = repository.getAllInspectorInfoByInspectionId(dto.getInspectionId());
        if (inspectors == null || inspectors.isEmpty()) {
            throw new RuntimeException("Inspektorlar ro'yxati bo'sh");
        }
        StringBuilder inspectorsFullInfo = new StringBuilder();
        for (InspectorInfoForInspectionAct inspector : inspectors) {
            inspectorsFullInfo.append(inspector.getOfficeName())
                    .append(" ")
                    .append("inspektori")
                    .append(" ")
                    .append(inspector.getInspectorName());
        }

        StringBuilder inspectorList = new StringBuilder();
        for (InspectorInfoForInspectionAct inspector : inspectors) {
            inspectorList.append(inspector.getOfficeName())
                    .append("<div class=\"signature-text\">")
                    .append("<p class=\"bold\">")
                    .append(inspector.getOfficeName())
                    .append(" inspektori:</p>")
                    .append("<p>")
                    .append(inspector.getInspectorName())
                    .append("</p>")
                    .append("</div>");
        }

        // Defects
        List<InspectionReportForAct> defects = inspectionReportService.getAllByInspectionId(dto.getInspectionId());
        if (defects == null || defects.isEmpty()) {
            throw new RuntimeException("Kamchiliklar ro'yxati bo'sh");
        }
        StringBuilder defectsFullInfo = new StringBuilder();
        for (int i = 1; i <= defects.size(); i++) {
            defectsFullInfo.append("<tr>")
                    .append("<td>")
                    .append(i)
                    .append("</td>")
                    .append("<td style=\"text-align: left;\">")
                    .append(defects.get(i - 1).getDefect())
                    .append("</td>")
                    .append("<td>")
                    .append(defects.get(i - 1).getDeadline())
                    .append("</td>")
                    .append("</tr>");
        }

        Map<String, String> parameters = new HashMap<>();

        parameters.put("officeName", userOffice.getName());
        parameters.put("districtName", dto.getDistrictName());
        parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        parameters.put("pronoun", inspectors.size() == 1 ? "Men" : "Biz");
        parameters.put("inspectors", inspectorsFullInfo.toString());
        parameters.put("specialCode", inspection.getSpecialCode());
        parameters.put("inspectionOfficeName", inspectionOffice.getName());
        parameters.put("decreeDate", date);
        parameters.put("decreeNumber", inspection.getDecreeNumber());
        parameters.put("legalAddress", inspectionProfile.getLegalAddress());
        parameters.put("legalName", inspectionProfile.getLegalName());
        parameters.put("tin", inspection.getTin().toString());
        parameters.put("objects", dto.getObjects());
        parameters.put("directorName", inspectionProfile.getFullName());
        parameters.put("sectionFirst", dto.getSectionFirst());
        parameters.put("sectionSecond", dto.getSectionSecond());
        parameters.put("sectionThird", defectsFullInfo.toString());
        parameters.put("sectionFourth", dto.getSectionFourth());
        parameters.put("sectionFifth", dto.getSectionFifth());
        parameters.put("sectionSixth", dto.getSectionSixth());
        parameters.put("inspectorList", inspectorList.toString());

        Template template = templateService.getByType(TemplateType.REPLY_INSPECTION_REPORT.name());

        // Save to an attachment and folder & Return a file path
        return attachmentService.createPdfFromHtml(template.getContent(), "appeals/reply/inspection", parameters, true);
    }
}
