package uz.technocorp.ecosystem.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
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

    @Override
    public String getUsername() {
        return this.tin.toString();
    }

    @Override
    public String getRole() {
        return Role.LEGAL.name();
    }

    @Override
    public List<String> getDirections() {
        return List.of(Direction.APPEAL.name());
    }

    @Override
    public String getName() {
        return this.legalName;
    }

    @Override
    public Long getPin() {
        return null;
    }

    @Override
    public Integer getDepartmentId() {
        return null;
    }

    @Override
    public Integer getOfficeId() {
        return null;
    }

    @Override
    public String getPosition() {
        return null;
    }

    @Override
    public String getLegalOwnershipType() {
        return this.legalOwnershipType;
    }

    @Override
    public String getLegalForm() {
        return this.legalForm;
    }
}
