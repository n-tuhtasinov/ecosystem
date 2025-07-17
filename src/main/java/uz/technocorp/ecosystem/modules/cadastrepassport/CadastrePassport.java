package uz.technocorp.ecosystem.modules.cadastrepassport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.06.2025
 * @since v1.0
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CadastrePassport extends BaseEntity {

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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class)
    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @Column(nullable = false)
    private String hfName;

    @Column(nullable = false)
    private String hfAddress;

    @Column(unique = true)
    private String registryNumber;

    @Column(nullable = false)
    private Integer hfRegionId;

    @Column(nullable = false)
    private Integer hfDistrictId;

    @Column(nullable = false)
    private Long organizationTin;

    @Column(nullable = false)
    private String organizationName;

    @Column(nullable = false)
    private String organizationAddress;

    @Column(nullable = false)
    private String location;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private Map<String, String> files;
}
