package uz.technocorp.ecosystem.modules.dangerousobjecttype;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.dangerousobjecttype.dto.DangerousObjectTypeDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface DangerousObjectTypeService {

    void create(DangerousObjectTypeDto dto);
    void update(Integer id, DangerousObjectTypeDto dto);
    void delete(Integer id);
    DangerousObjectType getById(Integer id);
    List<DangerousObjectType> getAll(String search);
    Page<DangerousObjectType> getAllPage(int page, int size, String search);
}
