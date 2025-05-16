package uz.technocorp.ecosystem.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uz.technocorp.ecosystem.modules.user.enums.Direction;
import uz.technocorp.ecosystem.modules.user.enums.Role;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 15.02.2025
 * @since v1.0
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IndividualUserDto implements UserDto{

    private String fullName;
    private Long pin;
    private String phoneNumber;

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.pin.toString();
    }

    @Override
    @JsonIgnore
    public String getRole() {
        return Role.INDIVIDUAL.name();
    }

    @Override
    @JsonIgnore
    public List<String> getDirections() {
        return List.of(Direction.APPEAL.name());
    }

    @Override
    @JsonIgnore
    public String getName() {
        return this.fullName;
    }

    @Override
    @JsonIgnore
    public Long getTin() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getLegalName() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getLegalAddress() {
        return null;
    }

    @Override
    @JsonIgnore
    public Integer getDepartmentId() {
        return null;
    }

    @Override
    @JsonIgnore
    public Integer getOfficeId() {
        return null;
    }

    @Override
    @JsonIgnore
    public Integer getRegionId() {
        return null;
    }

    @Override
    @JsonIgnore
    public Integer getDistrictId() {
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
        return null;
    }

    @Override
    @JsonIgnore
    public String getLegalForm() {
        return null;
    }
}
