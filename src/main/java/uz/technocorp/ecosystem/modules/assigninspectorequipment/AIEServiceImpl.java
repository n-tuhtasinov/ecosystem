package uz.technocorp.ecosystem.modules.assigninspectorequipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.assigninspectorequipment.dto.AIDto;
import uz.technocorp.ecosystem.modules.user.dto.InspectorDto;

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
    public InspectorDto getInspector(UUID assignId) {
        return repository
                .findInspector(assignId)
                .orElseThrow(() -> new ResourceNotFoundException("Tayinlov", "Id", assignId));
    }
}
