package uz.technocorp.ecosystem.modules.declaration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Declaration extends BaseEntity {

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false)
    private String legalAddress;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacility.class, optional = false)
    @JoinColumn(name = "hf_id", insertable = false, updatable = false)
    private HazardousFacility hf;

    @Column(name = "hf_id", nullable = false)
    private UUID hfId;

    @Column(nullable = false)
    private String hfName;

    @Column(nullable = false)
    private String hfRegistryNumber;

    @Column(nullable = false)
    private String hfAddress;

    @Column(unique = true)
    private String registryNumber;

    @Column(nullable = false)
    private Integer hfRegionId;

    @Column(nullable = false)
    private Integer hfDistrictId;

    @Column(nullable = false, columnDefinition = "text")
    private String information;

    @Column(nullable = false)
    private Long producingOrganizationTin;

    @Column(nullable = false)
    private String producingOrganizationName;

    @Column(nullable = false)
    private String operatingOrganizationName;

    @Column(nullable = false)
    private String expertiseNumber;

    @Column(nullable = false)
    private LocalDate expertiseDate;

    private String registrationOrganizationName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private Map<String , String> files;
}
