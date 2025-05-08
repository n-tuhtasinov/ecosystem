package uz.technocorp.ecosystem.modules.region;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.shared.AppConstants;
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
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;


    @Override
    public void create(RegionDto dto) {
        regionRepository.save(Region.builder()
                .name(dto.name())
                .soato(dto.soato())
                .number(dto.number())
                .build());
    }

    @Override
    public void update(Integer regionId, RegionDto dto) {
        Region region = regionRepository.findById(regionId).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", regionId));
        region.setName(dto.name());
        region.setSoato(dto.soato());
        regionRepository.save(region);
    }

    @Override
    public void deleteById(Integer regionId) {
        regionRepository.deleteById(regionId);
    }

    @Override
    public Page<RegionView> getAll(Map<String, String> params) {
        Pageable pageable= PageRequest.of(
                Integer.parseInt(params.getOrDefault("page", AppConstants.DEFAULT_PAGE_NUMBER))-1,
                Integer.parseInt(params.getOrDefault("size", AppConstants.DEFAULT_PAGE_SIZE)),
                Sort.Direction.DESC,
                "soato");

        return regionRepository.getAll(pageable);
    }

    @Override
    public List<RegionViewBySelect> getAllBySelect() {
        return regionRepository.getAllBySelect();
    }

    @Override
    public Region getById(Integer regionId) {
        return regionRepository.findById(regionId).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", regionId));
    }
}
