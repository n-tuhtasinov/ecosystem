package uz.technocorp.ecosystem.modules.assigninspectorequipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.assigninspectorequipment.dto.AIDto;

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
public class AIEServiceImpl implements AIEService {

    private final AIERepository repository;

    @Override
    public void create(AIDto dto) {
        Optional<AssignInspectorEquipment> optionalAssignInspectorEquipment = repository.findByEquipmentIdAndIntervalId(dto.objectId(), dto.intervalId());
        if (optionalAssignInspectorEquipment.isPresent()) {
            throw new RuntimeException("Ushbu XICHO uchun inspektor tayinlangan!");
        }
        repository.save(
                AssignInspectorEquipment
                        .builder()
                        .intervalId(dto.intervalId())
                        .equipmentId(dto.objectId())
                        .inspectorId(dto.inspectorId())
                        .build()
        );
    }

    @Override
    public void update(UUID id, AIDto dto) {
        AssignInspectorEquipment assignInspectorEquipment = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tayinlov", "Id", id));
        assignInspectorEquipment.setIntervalId(dto.intervalId());
        assignInspectorEquipment.setInspectorId(dto.inspectorId());
        assignInspectorEquipment.setEquipmentId(dto.objectId());
        repository.save(assignInspectorEquipment);
    }

    @Override
    public AssignInspectorInfo getInspectorInfo(UUID assignId) {
        return repository
                .findInfo(assignId)
                .orElseThrow(() -> new ResourceNotFoundException("Tayinlov", "Id", assignId));
    }
}
