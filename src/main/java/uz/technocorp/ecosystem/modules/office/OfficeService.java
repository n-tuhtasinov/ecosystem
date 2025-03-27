package uz.technocorp.ecosystem.modules.office;


import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.office.dto.OfficeDto;
import uz.technocorp.ecosystem.modules.office.projection.OfficeView;
import uz.technocorp.ecosystem.modules.office.projection.OfficeViewById;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface OfficeService {
    void create(OfficeDto dto);

    void update(Integer officeId, OfficeDto dto);

    void deleteById(Integer officeId);

    Page<OfficeView> getAll(Map<String, String> params);

    List<Office> getAllBySelect();

    OfficeViewById getById(Integer officeId);
}
