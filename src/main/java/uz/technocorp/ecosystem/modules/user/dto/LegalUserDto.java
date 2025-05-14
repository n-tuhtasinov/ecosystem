package uz.technocorp.ecosystem.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uz.technocorp.ecosystem.modules.user.enums.Direction;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @since v1.0
 * @created 15.02.2025
 * @description The legal user has only "appeal" in the direction list when it is first created
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LegalUserDto implements UserDto{

    private Long tin;

    private String legalName;

    private String legalAddress;

    private String fullName;

    private Integer regionId;

    private Integer districtId;

    private String phoneNumber;

    private String legalOwnershipType;  // mulkchilik shakli

    private String legalForm; // tashkiliy-huquqiy shakli

    private Integer officeId; //tashkilot qaysi officega tegishli (profilaktika uchun kerak)

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.tin.toString();
    }

    @Override
    @JsonIgnore
    public String getRole() {
        return Role.LEGAL.name();
    }

    @Override
    @JsonIgnore
    public List<String> getDirections() {
        return List.of(Direction.APPEAL.name());
    }

    @Override
    @JsonIgnore
    public String getName() {
        return this.legalName;
    }

    @Override
    @JsonIgnore
    public Long getPin() {
        return null;
    }

    @Override
    @JsonIgnore
    public Integer getDepartmentId() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getPosition() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getLegalOwnershipType() {
        return this.legalOwnershipType;
    }

    @Override
    @JsonIgnore
    public String getLegalForm() {
        return this.legalForm;
    }
}
