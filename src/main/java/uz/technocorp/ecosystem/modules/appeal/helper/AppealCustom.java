package uz.technocorp.ecosystem.modules.appeal.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.appeal.enums.OwnerType;

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
@NoArgsConstructor
public class AppealCustom {
    private UUID id;
    private LocalDate createdAt;
    private AppealStatus status;
    private Long ownerIdentity;
    private String number;
    private String ownerName;
    private String address;
    private String phoneNumber;
    private AppealType appealType;
    private String executorName;
    private LocalDate deadline;
    private String officeName;
    private Boolean isRejected;
    private String departmentName;
    private OwnerType ownerType;

    public AppealCustom(UUID id, LocalDateTime createdAt, AppealStatus status, Long ownerIdentity, String number, String ownerName, String address, String phoneNumber, AppealType appealType, String executorName, LocalDate deadline, String officeName, Boolean isRejected, String departmentName, OwnerType ownerType) {
        this.id = id;
        this.createdAt = createdAt.toLocalDate();
        this.status = status;
        this.ownerIdentity = ownerIdentity;
        this.number = number;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.appealType = appealType;
        this.executorName = executorName;
        this.deadline = deadline;
        this.officeName = officeName;
        this.isRejected = isRejected;
        this.departmentName = departmentName;
        this.ownerType = ownerType;
    }
}
