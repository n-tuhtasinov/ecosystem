package uz.technocorp.ecosystem.modules.appeal.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.03.2025
 * @since v1.0
 */
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class AppealCustom {
    private UUID id;
    private LocalDate createdDate;
    private AppealStatus status;
    private Long legalTin;
    private String number;
    private String legalName;
    private String regionName;
    private String districtName;
    private String address;
    private String phoneNumber;
    private AppealType appealType;
    private String executorName;
    private LocalDate deadline;
    private String officeName;

    public AppealCustom(UUID id, LocalDateTime createdAt, AppealStatus status, Long legalTin, String number, String legalName, String regionName, String districtName, String address, String phoneNumber, AppealType appealType, String executorName, LocalDate deadline, String officeName) {
        this.id = id;
        this.createdDate = createdAt.toLocalDate();
        this.status = status;
        this.legalTin = legalTin;
        this.number = number;
        this.legalName = legalName;
        this.regionName = regionName;
        this.districtName = districtName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.appealType = appealType;
        this.executorName = executorName;
        this.deadline = deadline;
        this.officeName = officeName;
    }
}
