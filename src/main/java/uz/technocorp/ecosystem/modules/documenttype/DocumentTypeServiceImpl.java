package uz.technocorp.ecosystem.modules.documenttype;

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
 * @created 19.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;

    @Override
    public void create(SimpleDto dto) {
        repository.save(new DocumentType(dto.name()));
    }

    @Override
    public void update(Integer id, SimpleDto dto) {
        DocumentType documentType = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hujjat turi", "Id", id));
        documentType.setName(dto.name());
        repository.save(documentType);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Page<DocumentType> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.ASC, "name");
        return repository.findAll(pageable);
    }

    @Override
    public List<DocumentType> getAll() {
        return repository.findAll();
    }
}
