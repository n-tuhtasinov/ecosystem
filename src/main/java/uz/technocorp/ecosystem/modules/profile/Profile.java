package uz.technocorp.ecosystem.modules.profile;

import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.department.Department;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends AuditEntity {

    private Long tin;
    private String legalName;
    private String fullName;
    private Long pin;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY )
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;

    @Column(name = "department_id")
    private Integer departmentId;





}
