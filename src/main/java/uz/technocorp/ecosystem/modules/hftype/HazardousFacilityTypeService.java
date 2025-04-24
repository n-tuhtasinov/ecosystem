package uz.technocorp.ecosystem.modules.hftype;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.hftype.dto.HazardousFacilityTypeDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface HazardousFacilityTypeService {

    void create(HazardousFacilityTypeDto dto);
    void update(Integer id, HazardousFacilityTypeDto dto);
    void delete(Integer id);
    HazardousFacilityType getById(Integer id);
    List<HazardousFacilityType> getAll(String search);
    Page<HazardousFacilityType> getAllPage(int page, int size, String search);
}
