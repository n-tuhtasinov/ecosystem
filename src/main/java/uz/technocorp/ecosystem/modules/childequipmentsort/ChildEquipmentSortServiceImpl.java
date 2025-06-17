package uz.technocorp.ecosystem.modules.childequipmentsort;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.shared.AppConstants;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipment;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.childequipmentsort.dto.ChildEquipmentSortDto;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortView;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortViewById;
import uz.technocorp.ecosystem.modules.childequipmentsort.view.ChildEquipmentSortViewBySelect;

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
public class ChildEquipmentSortServiceImpl implements ChildEquipmentSortService {

    private final ChildEquipmentSortRepository childEquipmentSortRepository;
    private final ChildEquipmentService childEquipmentService;

    @Override
    public void create(ChildEquipmentSortDto dto) {
        childEquipmentSortRepository.save(ChildEquipmentSort.builder()
                .name(dto.name())
                .childEquipmentId(dto.childEquipmentId())
                .build());
    }

    @Override
    public Page<ChildEquipmentSortView> getAllByPage(Map<String, String> params, Integer childEquipmentId) {

        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER)) - 1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "createdAt");

        return childEquipmentSortRepository.getAllByPage(pageable, childEquipmentId);

    }

    @Override
    public List<ChildEquipmentSortViewBySelect> getAll(Integer childEquipmentId) {
        return childEquipmentSortRepository.findByChildEquipmentId(childEquipmentId);
    }

    @Override
    public void update(Integer childEquipmentSortId, ChildEquipmentSortDto dto) {
        ChildEquipmentSort sort = findById(childEquipmentSortId);
        sort.setName(dto.name());
        sort.setChildEquipmentId(dto.childEquipmentId());
        childEquipmentSortRepository.save(sort);
    }

    private ChildEquipmentSort findById(Integer childEquipmentSortId) {
        return childEquipmentSortRepository.findById(childEquipmentSortId).orElseThrow(() -> new ResourceNotFoundException("Child equipment", "IDsi", childEquipmentSortId));
    }

    @Override
    public ChildEquipmentSortViewById getById(Integer childEquipmentSortId) {
        return childEquipmentSortRepository.getSortById(childEquipmentSortId).orElseThrow(() -> new ResourceNotFoundException("ChildEquipmentSort", "IDsi", childEquipmentSortId));
    }


    @Override
    public String getNameById(Integer childEquipmentSortId) {
        return findById(childEquipmentSortId).getName();
    }

    @Override
    public void deleteById(Integer childEquipmentSortId) {
        childEquipmentSortRepository.deleteById(childEquipmentSortId);
    }
}
