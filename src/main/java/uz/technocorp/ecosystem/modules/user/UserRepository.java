package uz.technocorp.ecosystem.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
}
