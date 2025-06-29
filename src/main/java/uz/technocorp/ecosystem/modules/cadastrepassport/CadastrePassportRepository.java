package uz.technocorp.ecosystem.modules.cadastrepassport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
public interface CadastrePassportRepository extends JpaRepository<CadastrePassport, UUID>, CadastrePassportRepo {
}
