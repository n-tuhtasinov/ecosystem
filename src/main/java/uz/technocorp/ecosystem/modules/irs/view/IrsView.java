package uz.technocorp.ecosystem.modules.irs.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.modules.irs.enums.IrsCategory;
import uz.technocorp.ecosystem.modules.irs.enums.IrsUsageType;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 09.06.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IrsView {
    private UUID id;
    private LocalDate registrationDate;
    private String registryNumber;
    private String address;
    private String symbol;
    private String sphere;
    private String factoryNumber;
    private Integer activity;
    private IrsCategory category;
    private Boolean isValid;
    private IrsUsageType usageType;
    private String legalName;
    private Long legalTin;
    private String legalAddress;

}
