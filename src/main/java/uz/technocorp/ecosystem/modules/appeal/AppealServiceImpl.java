package uz.technocorp.ecosystem.modules.appeal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.applicationexecutionprocess.AppealExecutionProcess;
import uz.technocorp.ecosystem.modules.applicationexecutionprocess.AppealExecutionProcessRepository;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository repository;
    private final AppealExecutionProcessRepository appealExecutionProcessRepository;

    @Override
    @Transactional
    public void setInspector(UUID inspector_id, UUID appeal_id) {
        Appeal appeal = repository
                .findById(appeal_id)
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", appeal_id));
        appeal.setInspectorId(inspector_id);
        repository.save(appeal);
        repository.flush();
        appealExecutionProcessRepository.save(
                new AppealExecutionProcess(
                        appeal_id,
                        "Ariza inspektorga biriktirildi!"
                )
        );
    }
}
