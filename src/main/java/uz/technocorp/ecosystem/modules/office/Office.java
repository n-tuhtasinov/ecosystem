package uz.technocorp.ecosystem.modules.office;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.region.Region;

import java.util.List;

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
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY)
    private List<Region> regions;
}
