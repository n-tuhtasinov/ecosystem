package uz.technocorp.ecosystem.modules.appeal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppealRepository extends JpaRepository<Appeal, UUID> {
}
