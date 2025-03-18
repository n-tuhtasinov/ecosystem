package uz.technocorp.ecosystem.modules.district;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.modules.district.dto.DistrictDto;
import uz.technocorp.ecosystem.modules.district.projection.DistrictView;
import uz.technocorp.ecosystem.modules.district.projection.DistrictViewBySelect;

import java.util.List;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;


    @Override
    public void create(DistrictDto dto) {
        districtRepository.save(District.builder()
                .name(dto.name())
                .soato(dto.soato())
                .number(dto.number())
                .regionId(dto.regionId())
                .build());
    }

    @Override
    public void update(Integer districtId, DistrictDto dto) {
        District district = districtRepository.findById(districtId).orElseThrow(() -> new ResourceNotFoundException("Tuman", "ID", districtId));
        district.setName(dto.name());
        district.setSoato(dto.soato());
        district.setRegionId(dto.regionId());
        districtRepository.save(district);
    }

    @Override
    public void deleteById(Integer districtId) {
        districtRepository.deleteById(districtId);
    }

    @Override
    public Page<DistrictView> getAll(Map<String, String> params) {
        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER))-1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "name");

        return districtRepository.getAllByRegionIdAndName(pageable, params.getOrDefault("regionId", null), params.getOrDefault("search", null));
    }

    @Override
    public List<DistrictViewBySelect> getAllBySelect(Integer regionId) {
        return districtRepository.getAllBySelect(regionId);
    }
}
