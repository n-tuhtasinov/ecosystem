package uz.technocorp.ecosystem.modules.inspectionreport;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.inspectionreport.dto.InspectionReportDto;
import uz.technocorp.ecosystem.modules.inspectionreport.view.InspectionReportView;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
public interface InspectionReportService {

    void create(UUID inspectionId, InspectionReportDto dto);
    void update(User user, UUID id, InspectionReportDto dto);
    void confirm(User user, UUID id);
    Page<InspectionReportView> getAllByInspectionId(User user, UUID inspectionId, int page, int size, boolean eliminated);
    List<InspectionReportView> getAllByInspectionId( UUID inspectionId);
}
