package uz.technocorp.ecosystem.modules.hftype;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.hftype.dto.HfTypeDto;

import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface HfTypeService {

    void create(HfTypeDto dto);

    void update(Integer id, HfTypeDto dto);

    void delete(Integer id);

    HfType getById(Integer id);

    List<HfType> getAll(String search);

    Page<HfType> getAllPage(int page, int size, String search);

    String getHfTypeNameById(Integer hfTypeId);

}
