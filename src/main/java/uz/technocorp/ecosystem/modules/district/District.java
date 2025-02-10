package uz.technocorp.ecosystem.modules.district;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.region.Region;

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
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false,unique = true)
    private Integer soato;

    @ManyToOne(targetEntity = Region.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    private Region region;

    @Column(name = "region_id", nullable = false)
    private Integer regionId;

}
