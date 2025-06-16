package uz.technocorp.ecosystem.modules.assigninspectorirs;

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
public class AIIRSServiceImpl implements AIIRSService {

    private final AIIRSRepository repository;

    @Override
    public void create(AIDto dto) {
        Optional<AssignInspectorIrs> optionalAssignInspectorIrs = repository.findByInspectorIdAndIntervalId(dto.inspectorId(), dto.intervalId());
        if (optionalAssignInspectorIrs.isPresent()) {
            throw  new RuntimeException("Bu INM uchun Inspektor tayinlangan!");
        }
        repository.save(
                AssignInspectorIrs
                        .builder()
                        .intervalId(dto.intervalId())
                        .irsId(dto.objectId())
                        .inspectorId(dto.inspectorId())
                        .build()
        );
    }

    @Override
    public void update(UUID id, AIDto dto) {
        AssignInspectorIrs assignInspectorIrs = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tayinlov", "Id", id));
        assignInspectorIrs.setIntervalId(dto.intervalId());
        assignInspectorIrs.setInspectorId(dto.inspectorId());
        assignInspectorIrs.setIrsId(dto.objectId());
        repository.save(assignInspectorIrs);
    }

    @Override
    public AssignInfoDto getInspectorInfo(UUID assignId) {
        return repository
                .findInfo(assignId)
                .orElseThrow(() -> new ResourceNotFoundException("Tayinlov", "Id", assignId));
    }
}
