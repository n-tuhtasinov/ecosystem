package uz.technocorp.ecosystem.modules.declaration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
public interface DeclarationRepository extends JpaRepository<Declaration, UUID>, DeclarationRepo{
}
