package uz.technocorp.ecosystem.shared;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 * @description automatically audit while creating and updating the entity
 */
@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
