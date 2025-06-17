package uz.technocorp.ecosystem.modules.inspection;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionActDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionDto;
import uz.technocorp.ecosystem.modules.inspection.dto.InspectionUpdateDto;
import uz.technocorp.ecosystem.modules.inspection.enums.InspectionStatus;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionCustom;
import uz.technocorp.ecosystem.modules.inspection.helper.InspectionFullDto;
import uz.technocorp.ecosystem.modules.inspection.view.InspectionView;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 23.05.2025
 * @since v1.0
 */
public interface InspectionService {


    void update(UUID id, InspectionDto dto);
    void update(UUID id, InspectionUpdateDto dto);
    void updateStatus(UUID id, InspectionStatus status);
    Page<InspectionCustom> getAll(User user, int page, int size, Long tin, InspectionStatus status, Integer intervalId);
    InspectionFullDto getById(UUID id);
}
