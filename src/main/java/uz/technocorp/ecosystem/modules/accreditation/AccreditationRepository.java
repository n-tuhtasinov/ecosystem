package uz.technocorp.ecosystem.modules.accreditation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.06.2025
 * @since v1.0
 */
public interface AccreditationRepository extends JpaRepository<Accreditation, UUID> {
}
