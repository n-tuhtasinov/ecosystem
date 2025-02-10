package uz.technocorp.ecosystem.modules.region;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.office.Office;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.01.2025
 * @since v1.0
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false,unique = true)
    private Integer soato;

    @ManyToOne(targetEntity = Office.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "office_id", updatable = false, insertable = false)
    private Office office;

    @Column(nullable = false, name = "office_id")
    private Integer officeId;
}
