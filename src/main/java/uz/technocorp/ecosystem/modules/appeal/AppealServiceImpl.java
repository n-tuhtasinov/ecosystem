package uz.technocorp.ecosystem.modules.appeal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealStatusDto;
import uz.technocorp.ecosystem.modules.appeal.dto.SetInspectorDto;
import uz.technocorp.ecosystem.modules.appeal.helper.AppealCustom;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcess;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessRepository;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;

import java.time.LocalDate;
import java.util.Map;

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
    private final UserRepository userRepository;
    private final AppealRepository appealRepository;

    @Override
    @Transactional
    public void setInspector(SetInspectorDto dto) {
        User user = userRepository
                .findById(dto.inspectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Inspektor", "Id", dto.inspectorId()));
        Appeal appeal = repository
                .findById(dto.appealId())
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", dto.appealId()));
        appeal.setInspectorId(dto.inspectorId());
        appeal.setInspectorName(user.getName());
        appeal.setDeadline(LocalDate.parse(dto.deadline()));
        repository.save(appeal);
        repository.flush();
        appealExecutionProcessRepository.save(
                new AppealExecutionProcess(
                        dto.appealId(),
                        "Ariza inspektorga biriktirildi!"
                )
        );
    }

    @Override
    @Transactional
    public void changeAppealStatus(AppealStatusDto dto) {
        Appeal appeal = repository
                .findById(dto.appealId())
                .orElseThrow(() -> new ResourceNotFoundException("Ariza", "Id", dto.appealId()));

        appeal.setStatus(dto.status());
        repository.save(appeal);
        appealExecutionProcessRepository.save(
                new AppealExecutionProcess(
                        dto.appealId(),
                        dto.description()
                )
        );
    }

    @Override
    public Page<AppealCustom> getAppealCustoms(Map<String, String> params) {
        return appealRepository.getAppealCustoms(params);
    }
}
