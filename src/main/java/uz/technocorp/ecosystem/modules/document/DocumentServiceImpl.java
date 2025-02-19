package uz.technocorp.ecosystem.modules.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;
import uz.technocorp.ecosystem.modules.documenttype.DocumentType;
import uz.technocorp.ecosystem.modules.documenttype.DocumentTypeRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repository;
    private final DocumentTypeRepository documentTypeRepository;

    @Override
    public void create(DocumentDto dto) {
        DocumentType documentType = documentTypeRepository
                .findById(dto.documentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Hujjat turi", "Id", dto.documentTypeId()));

        repository.save(
                new Document(
                        dto.path(),
                        dto.appealId(),
                        documentType.getName(),
                        false
                )
        );
    }

    @Override
    public List<DocumentProjection> findByAppealId(UUID appealId) {
        return repository.getByAppealId(appealId);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
