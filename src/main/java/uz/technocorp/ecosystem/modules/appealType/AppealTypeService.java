package uz.technocorp.ecosystem.modules.appealType;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.publics.dto.SimpleDto;

import java.util.List;

public interface AppealTypeService {

    void create(SimpleDto dto);
    void update(Integer id, SimpleDto dto);
    void delete(Integer id);
    AppealType getById(Integer id);
    List<AppealType> getAll(String search);
    Page<AppealType> getAllPage(int page, int size, String search);

}
