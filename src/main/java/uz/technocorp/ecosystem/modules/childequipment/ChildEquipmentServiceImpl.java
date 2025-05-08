package uz.technocorp.ecosystem.modules.childequipment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.modules.childequipment.dto.ChildEquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class ChildEquipmentServiceImpl implements ChildEquipmentService {

    private final ChildEquipmentRepository childEquipmentRepository;

    @Override
    public void create(ChildEquipmentDto dto) {
        childEquipmentRepository.save(ChildEquipment.builder()
                        .name(dto.name())
                        .equipmentType(dto.equipmentType())
                        .build());
    }

    @Override
    public Page<ChildEquipment> getAll(Map<String, String> params, EquipmentType type) {

        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER)) - 1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "createdAt");

        return childEquipmentRepository.findAllByEquipmentType(pageable, type);
    }

    @Override
    public List<ChildEquipment> getSelect(EquipmentType equipmentType) {
        return childEquipmentRepository.findByEquipmentType(equipmentType);
    }

    @Override
    public void update(Integer childEquipmentId, ChildEquipmentDto dto) {
        ChildEquipment child = childEquipmentRepository.findById(childEquipmentId).orElseThrow(() -> new ResourceNotFoundException("Child equipment", "ID", childEquipmentId));
        child.setName(dto.name());
        child.setEquipmentType(dto.equipmentType());
        childEquipmentRepository.save(child);
    }

    @Override
    public ChildEquipment getById(Integer equipmentId) {
        return childEquipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException("Child equipment", "ID", equipmentId));
    }
}
