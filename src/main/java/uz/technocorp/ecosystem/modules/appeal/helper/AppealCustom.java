package uz.technocorp.ecosystem.modules.appeal.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.03.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealCustom {
    private UUID id;
    private String date;
    private String status;
    private String legalTin;
    private String number;
    private String legalName;
    private String regionName;
    private String districtName;
    private UUID mainId;
    private String address;
    private String email;
    private String phoneNumber;
    private String appealType;
    private String executorName;
    private String deadline;
    private String officeName;
}
