package uz.technocorp.ecosystem.modules.irs;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.shared.BaseEntity;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.irs.enums.IrsCategory;
import uz.technocorp.ecosystem.modules.irs.enums.IrsIdentifierType;
import uz.technocorp.ecosystem.modules.irs.enums.IrsUsageType;
import uz.technocorp.ecosystem.modules.region.Region;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 24.03.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class IonizingRadiationSource extends BaseEntity {

    private String parentOrganization;

    private String address;

    @Column(nullable = false)
    private String supervisorName; //supervisor - mas'ul shaxs

    @Column(nullable = false)
    private String supervisorPosition;

    @Column(nullable = false)
    private String supervisorStatus;

    @Column(nullable = false)
    private String supervisorEducation;

    @Column(nullable = false)
    private String supervisorPhoneNumber;

    @Column(nullable = false)
    private String division;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IrsIdentifierType identifierType;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String sphere;

    @Column(nullable = false)
    private String factoryNumber;

    @Column(nullable = false)
    private Long orderNumber;

    @Column(nullable = false)
    private Integer activity; // aktivligi

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IrsCategory category;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private LocalDate manufacturedAt;

    @Column(nullable = false)
    private String acceptedFrom; // INM olingan tashkilot

    @Column(nullable = false)
    private LocalDate acceptedAt; // INM olingan sana

    @Column(nullable = false)
    private Boolean isValid; // INM xolati

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IrsUsageType usageType;

    private String storageLocation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, String> files;

    @ManyToOne(targetEntity = Region.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    private Region region;

    @Column(nullable = false, name = "region_id")
    private Integer regionId;

    @ManyToOne(targetEntity = District.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_id", updatable = false, insertable = false)
    private District district;

    @Column(nullable = false, name = "district_id")
    private Integer districtId;

    @ManyToOne(targetEntity = Appeal.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appeal_id", updatable = false, insertable = false)
    private Appeal appeal;

    @Column(name = "appeal_id")
    private UUID appealId;

    @Column(nullable = false, unique = true)
    private String registryNumber;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profile.class)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private Profile profile;

    @Column(name = "profile_id")
    private UUID profileId;

    @Column(nullable = false)
    private Long legalTin;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false)
    private String legalAddress;

    @Column(nullable = false)
    private LocalDate registrationDate;

    @Column(nullable = false)
    private String inspectorName;

    private LocalDate deactivationDate; // INM xolatini yaroqsiz holatda o'tkazilgan sana
}
