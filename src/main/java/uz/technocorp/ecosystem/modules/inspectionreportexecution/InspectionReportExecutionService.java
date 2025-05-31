package uz.technocorp.ecosystem.modules.inspectionreportexecution;

import uz.technocorp.ecosystem.modules.inspectionreportexecution.dto.IRExecutionDto;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 29.05.2025
 * @since v1.0
 */
public interface InspectionReportExecutionService {


    void create(UUID reportId, IRExecutionDto dto);
    void reject(User user, UUID id, IRExecutionDto dto);
    void accept(User user, UUID id);
}
