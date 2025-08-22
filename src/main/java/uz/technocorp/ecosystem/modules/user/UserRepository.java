package uz.technocorp.ecosystem.modules.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.technocorp.ecosystem.modules.user.enums.Role;
import uz.technocorp.ecosystem.modules.user.view.UserViewByInspectorPin;
import uz.technocorp.ecosystem.modules.user.view.UserViewByProfile;

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

    @Query("select u.id as id, p.directorName as fullName, p.identity as pin, p.id as profileId from User u join Profile p")
    Optional<UserViewByInspectorPin> getInspectorByPin(long pin, Role inspector);

    @Query(nativeQuery = true, value = """
            select u.id                   as id,
                   p.identity             as identity,
                   p.name                 as name,
                   p.legal_form           as legalForm,
                   p.legal_ownership_type as legalOwnershipType,
                   p.director_name        as directorName,
                   p.address              as address,
                   p.phone_number         as phoneNumber,
                   u.enabled              as isActive
            from users u
                     join profile p on u.profile_id = p.id
            where p.identity = :identity
            """)
    Optional<UserViewByProfile> getLegalAndIndividualUserByIdentity(Long identity);

    @Query(nativeQuery = true, value = """
            select u.*
            from users u
                     join profile p on u.profile_id = p.id
            where p.identity = :identity
            """)
    Optional<User> findUserByIdentity(Long identity);
}
