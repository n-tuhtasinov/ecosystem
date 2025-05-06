package uz.technocorp.ecosystem.modules.checklist;

import org.springframework.data.domain.Page;
import uz.technocorp.ecosystem.modules.checklist.dto.ChecklistDto;
import uz.technocorp.ecosystem.modules.checklist.view.ChecklistView;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
public interface ChecklistService {

    void create(User user, ChecklistDto checklistDto);
    void update(UUID id, User user, ChecklistDto checklistDto);
    void delete(UUID id);
    Page<ChecklistView> getChecklists(int page, int size, Long tin);
    Page<ChecklistView> getChecklists(User user, int page, int size);
}
