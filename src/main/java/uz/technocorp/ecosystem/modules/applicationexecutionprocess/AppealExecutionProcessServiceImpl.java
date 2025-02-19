package uz.technocorp.ecosystem.modules.applicationexecutionprocess;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
@Service
@RequiredArgsConstructor
public class AppealExecutionProcessServiceImpl implements AppealExecutionProcessService {

    private final AppealExecutionProcessRepository repository;

    @Override
    public void writeExecutionProcess(AppealExecutionProcessDto dto) {
        repository.save(
                new AppealExecutionProcess(
                        dto.appealId(),
                        dto.description()
                )
        );
    }

    @Override
    public List<AppealExecutionProcessProjection> getAll(UUID appealId) {
        return repository.getAllByAppealId(appealId);
    }
}
