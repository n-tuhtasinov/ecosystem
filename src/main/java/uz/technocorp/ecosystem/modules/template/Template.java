package uz.technocorp.ecosystem.modules.template;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.user.enums.Direction;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 15.04.2025
 * @since v1.0
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer ord;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Direction type;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

}
