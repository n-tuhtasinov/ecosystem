package uz.technocorp.ecosystem.modules.appealtype;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.publics.dto.SimpleDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealTypeService {

    void create(SimpleDto dto);
    void update(Integer id, SimpleDto dto);
    void delete(Integer id);
    AppealType getById(Integer id);
    List<AppealType> getAll(String search);
    Page<AppealType> getAllPage(int page, int size, String search);

}
