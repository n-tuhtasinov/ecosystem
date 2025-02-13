package uz.technocorp.ecosystem.modules.appealType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 12.02.2025
 * @since v1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    public AppealType(String name) {
        this.name = name;
    }
}
