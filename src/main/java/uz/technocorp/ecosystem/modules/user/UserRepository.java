package uz.technocorp.ecosystem.modules.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.modules.user.helper.UserViewByInspectorPin;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.role in :roles")
    Page<User> findAllByRoles(Pageable pageable, Set<Role> roles);

    @Query("select u.id as id, p.fullName as fullName, p.pin as pin, p.id as profileId from User u join Profile p")
    Optional<UserViewByInspectorPin> getInspectorByPin(long pin, Role inspector);
}
