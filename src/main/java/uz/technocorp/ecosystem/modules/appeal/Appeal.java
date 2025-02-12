package uz.technocorp.ecosystem.modules.appeal;

import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.appealType.AppealType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.region.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appeal extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AppealType.class)
    @JoinColumn(name = "appeal_type_id", insertable = false, updatable = false)
    private AppealType appealType;

    @Column(name = "appeal_type_id")
    private Integer appealTypeId;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private String legal_tin;

    @Column(nullable = false)
    private String legalName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    private String regionName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = District.class)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;

    private String districtName;

    private UUID profileId;
}
