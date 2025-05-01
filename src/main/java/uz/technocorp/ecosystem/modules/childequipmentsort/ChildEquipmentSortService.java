package uz.technocorp.ecosystem.modules.childequipmentsort;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
public interface ChildEquipmentSortService {

    void create(@Valid ChildEquipmentSortDto dto);

    Page<ChildEquipmentSortView> getAllByPage(Map<String, String> params);

    List<ChildEquipmentSortViewBySelect> getAll(Integer childEquipmentId);

    void update(Integer childEquipmentSortId, ChildEquipmentSortDto dto);

    ChildEquipmentSortViewById getById(Integer childEquipmentSortId);
}

