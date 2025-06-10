package uz.technocorp.ecosystem.modules.hf;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.hf.dto.HfParams;
import uz.technocorp.ecosystem.modules.hf.helper.HfCustom;
import uz.technocorp.ecosystem.modules.user.User;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.05.2025
 * @since v1.0
 */
public interface HfRepo {

    Page<HfCustom> getHfCustoms(User user, HfParams params);

}
