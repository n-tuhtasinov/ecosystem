package uz.technocorp.ecosystem.modules.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcess;
import uz.technocorp.ecosystem.modules.appealexecutionprocess.AppealExecutionProcessRepository;
import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.enums.DocumentType;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;

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
    private final AppealExecutionProcessRepository appealExecutionProcessRepository;

    @Override
    @Transactional
    public void create(DocumentDto dto) {

        repository.save(
                new Document(
                        dto.path(),
                        dto.appealId(),
                        DocumentType.valueOf(dto.documentType()),
                        false
                )
        );
        repository.flush();
        appealExecutionProcessRepository
                .save(new AppealExecutionProcess(
                   dto.appealId(),
                        dto.documentType() + " yaratildi!"
                ));

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
