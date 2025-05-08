package uz.technocorp.ecosystem.modules.profile;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.shared.BaseEntity;
import uz.technocorp.ecosystem.modules.department.Department;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.region.Region;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile extends BaseEntity {

    @Column(unique = true)
    private Long tin;

    private String legalName;
    private String legalAddress;
    private String fullName;

    @Column(unique = true)
    private Long pin;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;

    @Column(name = "department_id")
    private Integer departmentId;

    @ManyToOne(targetEntity = Office.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "office_id", insertable = false, updatable = false)
    private Office office;

    @Column(name = "office_id")
    private Integer officeId;

    @ManyToOne(targetEntity = Region.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Column(name = "region_id")
    private Integer regionId;

    private String regionName;

    @ManyToOne(targetEntity = District.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private District district;

    @Column(name = "district_id")
    private Integer districtId;

    private String districtName;

    private String position;

    private String phoneNumber;

    private String legalOwnershipType; //for only legal (mulkchilik shakli)

    private String legalForm; //for only legal (tashkiliy-huquqiy shakli)
}
