package uz.technocorp.ecosystem.modules.hftype;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.hftype.dto.HfTypeDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class HfTypeServiceImpl implements HfTypeService {

    private final HfTypeRepository repository;

    @Override
    public void create(HfTypeDto dto) {
        repository.save(
                new HfType(
                        dto.name(),
                        dto.description()
                )
        );
    }

    @Override
    public void update(Integer id, HfTypeDto dto) {
        HfType hfType = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("XICHO turi", "Id", id));
        hfType.setDescription(dto.description());
        hfType.setName(dto.name());
        repository.save(hfType);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public HfType getById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("XICHO turi", "Id", id));
    }

    @Override
    public List<HfType> getAll(String search) {
        return repository.findAllByName(search);
    }

    @Override
    public Page<HfType> getAllPage(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "name");
        return repository.findAllPageByName(pageable, search);
    }

    @Override
    public String getHfTypeNameById(Integer hfTypeId) {
        return repository.findById(hfTypeId).orElseThrow(() -> new ResourceNotFoundException("XICHO turi", "ID", hfTypeId)).getName();
    }

}
