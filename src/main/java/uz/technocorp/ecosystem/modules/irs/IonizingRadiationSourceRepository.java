package uz.technocorp.ecosystem.modules.irs;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 22.04.2025
 * @since v1.0
 */
public interface IonizingRadiationSourceRepository extends JpaRepository<IonizingRadiationSource, UUID> {
}
