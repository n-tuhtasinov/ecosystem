package uz.technocorp.ecosystem.modules.assigninspectorhf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.assigninspectorequipment.dto.AIDto;
import uz.technocorp.ecosystem.modules.assigninspectorequipment.dto.AssignInfoDto;
import uz.technocorp.ecosystem.modules.user.dto.InspectorDto;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 08.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AIHFServiceImpl implements AIHFService {

    private final AIHFRepository repository;

    @Override
    public void create(AIDto dto) {
        Optional<AssignInspectorHf> optionalAssignInspectorHf = repository.findByInspectorIdAndIntervalId(dto.inspectorId(), dto.intervalId());
        if (optionalAssignInspectorHf.isPresent()) {
            throw new RuntimeException("Ushbu XICHO uchun inspektor tayinlangan!");
        }
        repository.save(
                AssignInspectorHf
                        .builder()
                        .intervalId(dto.intervalId())
                        .hfId(dto.objectId())
                        .inspectorId(dto.inspectorId())
                        .build()
        );
    }

    @Override
    public void update(UUID id, AIDto dto) {
        AssignInspectorHf assignInspectorHf = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tayinlov", "Id", id));
        assignInspectorHf.setIntervalId(dto.intervalId());
        assignInspectorHf.setInspectorId(dto.inspectorId());
        assignInspectorHf.setHfId(dto.objectId());
        repository.save(assignInspectorHf);
    }

    @Override
    public AssignInfoDto getInspectorInfo(UUID assignId) {
        return repository
                .findInfo(assignId)
                .orElseThrow(() -> new ResourceNotFoundException("Tayinlov", "Id", assignId));
    }
}
