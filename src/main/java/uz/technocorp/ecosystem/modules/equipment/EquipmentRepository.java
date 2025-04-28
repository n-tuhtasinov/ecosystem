package uz.technocorp.ecosystem.modules.equipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 28.04.2025
 * @since v1.0
 */
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
}
