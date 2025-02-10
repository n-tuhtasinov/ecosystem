package uz.technocorp.ecosystem.modules.profile;


import uz.technocorp.ecosystem.modules.user.dto.DepartmentalUserDto;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.01.2025
 * @since v1.0
 */
public interface ProfileService {
    UUID save(DepartmentalUserDto user);
}
