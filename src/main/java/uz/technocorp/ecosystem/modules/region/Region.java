package uz.technocorp.ecosystem.modules.region;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false, unique = true)
    private Integer number;

    @JsonIgnore
    @ManyToOne(targetEntity = Office.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "office_id", updatable = false, insertable = false)
    private Office office;

    @JsonIgnore
    @Column(name = "office_id")
    private Integer officeId;
}
