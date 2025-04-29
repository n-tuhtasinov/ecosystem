package uz.technocorp.ecosystem.modules.childequipment;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.childequipment.dto.ChildEquipmentDto;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public interface ChildEquipmentService {

    void create(ChildEquipmentDto childEquipmentDto);

    Page<ChildEquipment> getAll(Map<String, String> params);

    List<ChildEquipment> getSelect();

    void update(Integer childEquipmentId, ChildEquipmentDto childEquipmentDto);

    ChildEquipment getById(Integer equipmentId);
}
