package uz.technocorp.ecosystem.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.profile.enums.ProfileType;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 13.02.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChairmanUserDto implements UserDto {
    @NotBlank(message = "Hodim FIOsi jo'natilmadi")
    private String fullName;

    @NotNull(message = "Hodim JSHIRi jo'natilmadi")
    private Long pin;

    @NotBlank(message = "Hodim roli tanlanmadi")
    private String role;

    @NotEmpty(message = "Hodim bajaradigan ishlar tanlanmadi")
    private List<@NotBlank(message = "Directionga bo'sh String qo'shish mumkin emas") String> directions;

    @NotBlank(message = "Hodim lavozimi jo'natilmadi")
    private String position;

    @NotBlank(message = "Hodim telefon raqami jo'natilmadi")
    private String phoneNumber;

    @Override
    public String getUsername() {
        return this.pin.toString();
    }

    @Override
    public String getName() {
        return this.fullName;
    }

    @Override
    public Long getIdentity() {
        return this.pin;
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
    public ProfileType getType() {
        return ProfileType.EMPLOYEE;
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
    public String getLegalOwnershipType() {
        return null;
    }

    @Override
    public String getLegalForm() {
        return null;
    }
}
