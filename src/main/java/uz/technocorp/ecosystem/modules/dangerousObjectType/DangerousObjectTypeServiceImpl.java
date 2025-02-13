package uz.technocorp.ecosystem.modules.dangerousObjectType;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.dangerousObjectType.dto.DangerousObjectTypeDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class DangerousObjectTypeServiceImpl implements DangerousObjectTypeService {

    private final DangerousObjectTypeRepository repository;

    @Override
    public void create(DangerousObjectTypeDto dto) {
        repository.save(
                new DangerousObjectType(
                        dto.name(),
                        dto.description()
                )
        );
    }

    @Override
    public void update(Integer id, DangerousObjectTypeDto dto) {
        DangerousObjectType dangerousObjectType = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("XICHO turi", "Id", id));
        dangerousObjectType.setDescription(dto.description());
        dangerousObjectType.setName(dto.name());
        repository.save(dangerousObjectType);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public DangerousObjectType getById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("XICHO turi", "Id", id));
    }

    @Override
    public List<DangerousObjectType> getAll(String search) {
        return repository.findAllByName(search);
    }

    @Override
    public Page<DangerousObjectType> getAllPage(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.ASC, "name");
        return repository.findAllPageByName(pageable, search);
    }
}
