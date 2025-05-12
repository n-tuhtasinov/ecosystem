package uz.technocorp.ecosystem.modules.hftype;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.shared.AuditEntity;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HfType extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    public HfType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
