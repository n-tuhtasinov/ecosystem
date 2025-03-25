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
 * @created 15.02.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualUserDto implements UserDto{

    private String fullName;
    private Long pin;
    private String phoneNumber;

    @Override
    public String getUsername() {
        return this.pin.toString();
    }

    @Override
    public String getRole() {
        return Role.INDIVIDUAL.name();
    }

    @Override
    public List<String> getDirections() {
        return List.of(Direction.APPEAL.name());
    }

    @Override
    public String getName() {
        return this.fullName;
    }

    @Override
    public Long getTin() {
        return null;
    }

    @Override
    public String getLegalName() {
        return null;
    }

    @Override
    public String getLegalAddress() {
        return null;
    }

    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public Long getPin() {
        return this.pin;
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
    public Integer getRegionId() {
        return null;
    }

    @Override
    public Integer getDistrictId() {
        return null;
    }

    @Override
    public String getPosition() {
        return null;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public String getLegalOwnershipType() {
        return null;
    }

    @Override
    public String getLegalForm() {
        return null;
    }
}
