package uz.technocorp.ecosystem.modules.region;


import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.region.dto.RegionDto;
import uz.technocorp.ecosystem.modules.region.projection.RegionView;
import uz.technocorp.ecosystem.modules.region.projection.RegionViewBySelect;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface RegionService {
    void create(RegionDto dto);

    void update(Integer regionId, RegionDto dto);

    void deleteById(Integer regionId);

    Page<RegionView> getAll(Map<String, String> params);

    List<RegionViewBySelect> getAllBySelect();

    Region getById(Integer regionId);

}
