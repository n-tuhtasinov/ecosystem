package uz.technocorp.ecosystem.modules.appealDangerousObject;

import uz.technocorp.ecosystem.modules.appealDangerousObject.dto.AppealDangerousObjectDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.publics.AttachmentDto;

import java.util.Map;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealDangerousObjectService {

    void create(User user, AppealDangerousObjectDto dto);
    void update(UUID id, AppealDangerousObjectDto dto);
    void getById(UUID id);
    void setAttachments(AttachmentDto dto);
}
