package uz.technocorp.ecosystem.modules.office;


import uz.technocorp.ecosystem.modules.office.dto.OfficeDto;

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
}
