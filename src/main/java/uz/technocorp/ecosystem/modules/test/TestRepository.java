package uz.technocorp.ecosystem.modules.test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 04.06.2025
 * @since v1.0
 */
public interface TestRepository extends JpaRepository<TestEntity, UUID> {
}
