package uz.technocorp.ecosystem.modules.hazardousfacility;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.hazardousfacilityappeal.HazardousFacilityAppeal;
import uz.technocorp.ecosystem.modules.hazardousfacilitytype.HazardousFacilityType;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.region.Region;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 04.03.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HazardousFacility extends AuditEntity {

    @Column(nullable = false)
    private Long legal_tin;

    @Column(nullable = false)
    private String legalName;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = District.class)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profile.class)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_id")
    private UUID profileId;

    private String legalAddress;

    private String phoneNumber;

    private String email;

    private String upperOrganization;

    private String name;

    private String address;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacilityAppeal.class)
    @JoinColumn(name = "registration_appeal_id", insertable = false, updatable = false)
    private HazardousFacilityAppeal appeal;

    @Column(name = "registration_appeal_id")
    private UUID appealId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacilityType.class)
    @JoinColumn(name = "hazardous_facility_type_id", insertable = false, updatable = false)
    private HazardousFacilityType hazardousFacilityType;

    @Column(name = "hazardous_facility_type_id")
    private Integer hazardousFacilityTypeId;

    private String extraArea;

    private String description;

    private String objectNumber;

    public HazardousFacility(Long legal_tin, String legalName, Integer regionId, Integer districtId,
                             UUID profileId, String legalAddress, String phoneNumber, String email,
                             String upperOrganization, String name, String address, UUID appealId,
                             Integer hazardousFacilityTypeId, String extraArea, String description,
                             String objectNumber) {
        this.legal_tin = legal_tin;
        this.legalName = legalName;
        this.regionId = regionId;
        this.districtId = districtId;
        this.profileId = profileId;
        this.legalAddress = legalAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.upperOrganization = upperOrganization;
        this.name = name;
        this.address = address;
        this.appealId = appealId;
        this.hazardousFacilityTypeId = hazardousFacilityTypeId;
        this.extraArea = extraArea;
        this.description = description;
        this.objectNumber = objectNumber;
    }
}
