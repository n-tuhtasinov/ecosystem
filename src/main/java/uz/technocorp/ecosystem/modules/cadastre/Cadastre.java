//package uz.technocorp.ecosystem.modules.cadastre;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import uz.technocorp.ecosystem.shared.BaseEntity;
//
//import java.time.LocalDate;
//
///**
// * @author Nurmuhammad Tuhtasinov
// * @version 1.0
// * @created 26.06.2025
// * @since v1.0
// */
//@Entity
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class Cadastre extends BaseEntity {
//
//    @Column(nullable = false)
//    private String legalName;
//
//    @Column(nullable = false)
//    private Long legalTin;
//
//    @Column(nullable = false)
//    private String location;
//
//    @Column(nullable = false, unique = true)
//    private String cadastreNumber;
//
//    @Column(nullable = false, unique = true)
//    private String registryNumber;
//
//    @Column(nullable = false)
//    private LocalDate registryDate;
//
//    @Column(nullable = false)
//    private String totalArea;
//
//
//
//}
