package uz.technocorp.ecosystem.modules.dangerousobjecttype;

import jakarta.persistence.*;
import lombok.*;

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
public class DangerousObjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    public DangerousObjectType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
