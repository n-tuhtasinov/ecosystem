package uz.technocorp.ecosystem.modules.appeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.04.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IrsAcceptanceAppealDto implements AppealDto {

    @NotBlank(message = "Tashkilot telefon raqami jo'natilmadi")
    private String phoneNumber;

    @NotBlank(message = "Tashkilot emaili jo'natilmadi")
    private String email;

    @NotNull(message = "INM tanlanmadi")
    private UUID irsId;

    @NotNull(message = "INM joylashtiriladigan viloyat jo'natilmadi")
    private Integer regionId;  // aynan inm joylashadigan viloyat

    @NotNull(message = "INM joylashtiriladigan tuman jo'natilmadi")
    private Integer districtId; // aynan inm joylashadigan tuman

    @NotBlank(message = "INM joylashtiriladigan manzil jo'natilmadi")
    private String address; // aynan inm joylashadigan address

    @NotBlank(message = "Berish maqsadi jo'natilmadi")
    private String usageType;

    @NotNull(message = "Olish sanasi kiritilmadi")
    private LocalDate acceptedAt;

    @NotBlank(message = "INM saqlanadigan joy kiritilmadi")
    private String storageLocation;

    @NotBlank(message = "Ses xulosasi fayli yo'li jo'natilmadi")
    private String sesConclusionPath;

    private String additionalFilePath;

    @NotBlank(message = "Ariza fayli joylashgan path jo'natilmadi")
    private String appealPath;

    @Override
    public AppealType getAppealType() {
        return AppealType.ACCEPT_IRS;
    }

    @Override
    public LocalDate getDeadline() {
        return LocalDate.now().plusDays(15);
    }
}
