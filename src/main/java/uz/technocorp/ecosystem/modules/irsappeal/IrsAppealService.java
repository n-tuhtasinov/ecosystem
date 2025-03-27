package uz.technocorp.ecosystem.modules.irsappeal;

import jakarta.validation.Valid;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 27.03.2025
 * @since v1.0
 */
public interface IrsAppealService {

    void create(IrsDto irsDto);
}
