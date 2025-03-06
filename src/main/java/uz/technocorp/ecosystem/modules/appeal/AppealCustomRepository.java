package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealSearchCriteria;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.03.2025
 * @since v1.0
 */
public interface AppealCustomRepository {

    Page<AppealCustom> appealCustoms(Pageable pageable, AppealSearchCriteria criteria);
}
