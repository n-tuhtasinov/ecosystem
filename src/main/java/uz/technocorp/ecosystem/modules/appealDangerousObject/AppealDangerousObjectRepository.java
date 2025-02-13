package uz.technocorp.ecosystem.modules.appealDangerousObject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealDangerousObjectRepository extends JpaRepository<AppealDangerousObject, UUID> {

}
