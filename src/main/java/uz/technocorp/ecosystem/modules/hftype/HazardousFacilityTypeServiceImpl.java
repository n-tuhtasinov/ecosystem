package uz.technocorp.ecosystem.modules.hftype;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.hftype.dto.HazardousFacilityTypeDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class HazardousFacilityTypeServiceImpl implements HazardousFacilityTypeService {

    private final HazardousFacilityTypeRepository repository;

    @Override
    public void create(HazardousFacilityTypeDto dto) {
        repository.save(
                new HazardousFacilityType(
                        dto.name(),
                        dto.description()
                )
        );
    }

    @Override
    public void update(Integer id, HazardousFacilityTypeDto dto) {
        HazardousFacilityType hazardousFacilityType = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("XICHO turi", "Id", id));
        hazardousFacilityType.setDescription(dto.description());
        hazardousFacilityType.setName(dto.name());
        repository.save(hazardousFacilityType);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public HazardousFacilityType getById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("XICHO turi", "Id", id));
    }

    @Override
    public List<HazardousFacilityType> getAll(String search) {
        return repository.findAllByName(search);
    }

    @Override
    public Page<HazardousFacilityType> getAllPage(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "name");
        return repository.findAllPageByName(pageable, search);
    }
}
