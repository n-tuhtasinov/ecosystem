package uz.technocorp.ecosystem.modules.equipment;

import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.user.User;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
public interface EquipmentService {
    void create(Appeal appeal);

    void getAll(User user, Map<String, String> params);
}
