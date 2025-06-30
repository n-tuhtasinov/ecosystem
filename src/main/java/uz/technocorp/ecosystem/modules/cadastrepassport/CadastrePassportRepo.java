package uz.technocorp.ecosystem.modules.cadastrepassport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.technocorp.ecosystem.modules.cadastrepassport.dto.CadastrePassportParams;
import uz.technocorp.ecosystem.modules.cadastrepassport.view.CadastrePassportView;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
public interface CadastrePassportRepo {

    Page<CadastrePassportView> getAllByParams(CadastrePassportParams params);
}
