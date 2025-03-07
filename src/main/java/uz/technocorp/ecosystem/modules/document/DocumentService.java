package uz.technocorp.ecosystem.modules.document;

import uz.technocorp.ecosystem.modules.document.dto.DocumentDto;
import uz.technocorp.ecosystem.modules.document.projection.DocumentProjection;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public interface DocumentService {

    void create(DocumentDto dto);
    List<DocumentProjection> findByAppealId(UUID appealId);
    void delete(UUID id);

}
