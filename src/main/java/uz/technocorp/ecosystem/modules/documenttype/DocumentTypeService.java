package uz.technocorp.ecosystem.modules.documenttype;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.publics.dto.SimpleDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
public interface DocumentTypeService {

    void create(SimpleDto dto);
    void update(Integer id, SimpleDto dto);
    void delete(Integer id);
    Page<DocumentType> getAll(int page, int size);
    List<DocumentType> getAll();
}
