package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
public interface AppealRepository extends JpaRepository<Appeal, UUID>, AppealRepo {

}
