package uz.technocorp.ecosystem.modules.irsappeal;

import jakarta.validation.Valid;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 27.03.2025
 * @since v1.0
 */
public interface IrsAppealService {

    void create(User user, IrsDto irsDto);

    IrsAppeal getByAppealId(UUID appealId);
}
