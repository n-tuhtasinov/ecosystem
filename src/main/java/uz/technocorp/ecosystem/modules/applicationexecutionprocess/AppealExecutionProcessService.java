package uz.technocorp.ecosystem.modules.applicationexecutionprocess;

import uz.technocorp.ecosystem.modules.applicationexecutionprocess.dto.AppealExecutionProcessDto;
import uz.technocorp.ecosystem.modules.applicationexecutionprocess.projection.AppealExecutionProcessProjection;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public interface AppealExecutionProcessService {

    void writeExecutionProcess(AppealExecutionProcessDto dto);
    List<AppealExecutionProcessProjection> getAll(UUID appealId);
}
