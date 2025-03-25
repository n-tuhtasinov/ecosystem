package uz.technocorp.ecosystem.modules.irs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.technocorp.ecosystem.models.AuditEntity;
import uz.technocorp.ecosystem.modules.irs.enums.IrsCategory;
import uz.technocorp.ecosystem.modules.irs.enums.IrsIdentifierType;
import uz.technocorp.ecosystem.modules.irs.enums.IrsUsageType;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 24.03.2025
 * @since v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class IonizingRadiationSource extends AuditEntity {

    private String parentOrganization;

    @Column(nullable = false)
    private String supervisorName; //supervisor - mas'ul shaxs

    @Column(nullable = false)
    private String supervisorPosition;

    @Column(nullable = false)
    private String supervisorStatus;

    @Column(nullable = false)
    private String supervisorEducation;

    @Column(nullable = false)
    private String supervisorPhoneNumber;

    @Column(nullable = false)
    private String division;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IrsIdentifierType identifierType;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String sphere;

    @Column(nullable = false)
    private String factoryNumber;

    @Column(nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private Integer activity;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IrsCategory category;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private LocalDate manufacturedAt;

    @Column(nullable = false)
    private String acceptedFrom; // INM olingan tashkilot

    @Column(nullable = false)
    private String acceptedAt; // INM olingan sana

    @Column(nullable = false)
    private Boolean isValid; // INM xolati

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IrsUsageType usageType;

    private String storageLocation;

    @Column(nullable = false)
    private String passportPath;

    private String additionalFilePath;

    @Column(columnDefinition = "TEXT")
    private String description;

}
