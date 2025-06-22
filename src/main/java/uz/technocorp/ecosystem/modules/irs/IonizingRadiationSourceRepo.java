package uz.technocorp.ecosystem.modules.irs;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.irs.dto.IrsParams;
import uz.technocorp.ecosystem.modules.irs.view.IrsView;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 09.06.2025
 * @since v1.0
 */
public interface IonizingRadiationSourceRepo {

    Page<IrsView> getAll(IrsParams params);

    Long countByParams(Long tin, Integer regionId);
}
