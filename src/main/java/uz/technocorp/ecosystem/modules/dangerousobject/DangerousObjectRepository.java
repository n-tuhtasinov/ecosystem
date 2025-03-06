package uz.technocorp.ecosystem.modules.dangerousobject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
public interface DangerousObjectRepository extends JpaRepository<DangerousObject, UUID> {

}
