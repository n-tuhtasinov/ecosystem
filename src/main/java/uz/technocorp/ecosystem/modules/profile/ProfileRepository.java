package uz.technocorp.ecosystem.modules.profile;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.01.2025
 * @since v1.0
 */
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
}
