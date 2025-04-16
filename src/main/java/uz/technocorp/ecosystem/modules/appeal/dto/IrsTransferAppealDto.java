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
 * @created 09.04.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IrsTransferAppealDto implements AppealDto {

    @NotBlank(message = "Tashkilot telefon raqami jo'natilmadi")
    private String phoneNumber;

    @NotBlank(message = "Tashkilot emaili jo'natilmadi")
    private String email;

    @NotNull(message = "INM tanlanmadi")
    private UUID irsId;

    @NotNull(message = "INM joylashgan viloyat jo'natilmadi")
    private Integer regionId;  // aynan inm joylashgan viloyat

    @NotNull(message = "INM joylashgan tuman jo'natilmadi")
    private Integer districtId; // aynan inm joylashgan tuman

    @NotBlank(message = "INM joylashgan manzil jo'natilmadi")
    private String address; // aynan inm joylashgan address

    @NotNull(message = "Qabul qiluvchi tashkilot INNsi jo'natilmadi")
    private Long acceptedLegalTin;

    @NotBlank(message = "Qabul qiluvchi tashkilot nomi jo'natilmadi")
    private String acceptedOrganization;

    @NotBlank(message = "Qabul qiluvchi tashkilot viloyati jo'natilmadi")
    private String acceptedRegion;

    @NotBlank(message = "Qabul qiluvchi tashkilot tumani jo'natilmadi")
    private String acceptedDistrict;

    @NotBlank(message = "Qabul qiluvchi tashkilot manzili jo'natilmadi")
    private String acceptedAddress;

    @NotBlank(message = "Berish maqsadi jo'natilmadi")
    private String usageType;

    @NotNull(message = "Berish sanasi kiritilmadi")
    private LocalDate presentedAt;

    @NotNull(message = "INM aktivligi kiritilmadi")
    private Integer activity;

    @NotBlank(message = "Ses xulosasi fayli yo'li jo'natilmadi")
    private String sesConclusionPath;

    private String additionalFilePath;

    @NotBlank(message = "Ariza fayli joylashgan path jo'natilmadi")
    private String appealPath;

    @Override
    public AppealType getAppealType() {
        return AppealType.TRANSFER_IRS;
    }

    @Override
    public LocalDate getDeadline() {
        return LocalDate.now().plusDays(15);
    }
}
