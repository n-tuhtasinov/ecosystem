//package uz.technocorp.ecosystem.modules.irsappeal;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.JsonNode;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.JdbcTypeCode;
//import org.hibernate.type.SqlTypes;
//import uz.technocorp.ecosystem.models.AuditEntity;
//import uz.technocorp.ecosystem.modules.appeal.Appeal;
//import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
//import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
///**
// * @author Nurmuhammad Tuhtasinov
// * @version 1.0
// * @created 24.03.2025
// * @since v1.0
// * @description IRS - the abbreviation of 'Ionizing Radiation Sources'
// */
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//public class IrsAppeal extends AuditEntity {
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private AppealType appealType;
//
//    @Column(nullable = false, unique = true)
//    private String number;
//
//    @JsonIgnore
//    @Column(nullable = false)
//    private Integer orderNumber;
//
//    @Column(nullable = false)
//    private Long legalTin;
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private AppealStatus status;
//
//    private LocalDate deadline;
//
//    @Column(columnDefinition = "jsonb", nullable = false)
//    @JdbcTypeCode(SqlTypes.JSON)
//    private JsonNode data;
//
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Appeal.class, optional = false)
//    @JoinColumn(name = "appeal_id", insertable = false, updatable = false)
//    private Appeal appeal;
//
//    @JsonIgnore
//    @Column(name = "appeal_id", nullable = false)
//    private UUID appealId;
//
//}
