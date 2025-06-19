package uz.technocorp.ecosystem.modules.prevention;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Suxrob
 * @version 1.0
 * @created 19.06.2025
 * @since v1.0
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PreventionFile {

    @Id
    private String path;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer regionId;

}
