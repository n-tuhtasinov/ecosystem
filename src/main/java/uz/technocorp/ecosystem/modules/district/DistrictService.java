package uz.technocorp.ecosystem.modules.district;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.district.dto.DistrictDto;
import uz.technocorp.ecosystem.modules.district.projection.DistrictView;
import uz.technocorp.ecosystem.modules.district.projection.DistrictViewById;
import uz.technocorp.ecosystem.modules.district.projection.DistrictViewBySelect;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
public interface DistrictService {
    void create(@Valid DistrictDto dto);

    void update(Integer districtId, DistrictDto dto);

    void deleteById(Integer districtId);

    Page<DistrictView> getAll(Map<String, String> params);

    List<DistrictViewBySelect> getAllBySelect(Integer regionId);

    DistrictViewById getById(Integer districtId);

    District findById(Integer districtId);

    District findBySoato(Integer soato);
}
