package uz.technocorp.ecosystem.modules.appealtype;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.publics.dto.SimpleDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class AppealTypeServiceImpl implements AppealTypeService{

    private final AppealTypeRepository repository;

    @Override
    public void create(SimpleDto dto) {
        repository.save(
                new AppealType(dto.name())
        );
    }

    @Override
    public void update(Integer id, SimpleDto dto) {
        AppealType appealType = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ariza turi", "Id", id));
        appealType.setName(dto.name());
        repository.save(appealType);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public AppealType getById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ariza turi", "Id", id));
    }

    @Override
    public List<AppealType> getAll(String search) {
        return repository.findAllByName(search);
    }

    @Override
    public Page<AppealType> getAllPage(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, "name");
        return repository.findAllPagesByName(pageable, search);
    }
}
