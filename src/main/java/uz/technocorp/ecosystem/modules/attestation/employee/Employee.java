package uz.technocorp.ecosystem.modules.attestation.employee;


import jakarta.persistence.*;
import lombok.*;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Suxrob
 * @version 1.0
 * @created 17.06.2025
 * @since v1.0
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String pin;

    @Column(nullable = false)
    private String fullName;

    @Column
    private String certNumber;

    @Column
    private LocalDate certDate;

    @Column
    private LocalDate certExpiryDate;

    @Column
    @Enumerated(EnumType.STRING)
    private EmployeePosition position;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HazardousFacility.class)
    @JoinColumn(name = "hf_id", insertable = false, updatable = false)
    private HazardousFacility hf;

    @Column(name = "hf_id")
    private UUID hfId;

    /**
     * @goal XICHO xodimlarini attestatsiyadan o'tkazish kerak
     *
     * @case xodimlar 3 toifaga bo'linadi ( Rahbar, Injener, Oddiy xodim )
     * xodimlar 2-5 yil oraliqda qayta attestatsiyadan o'tadi
     *
     * Rahbar qo'mita orqali o'tadi, qolgan xodimlar hududiy bo'lim orqali
     *
     * Injener va oddiy xodimlar uchun attestatsiya jarayoni :
     * @process XICHO rahbar ariza shakllantirib ( xodimlar listini tayyorlab ) qo'mitaga ariza tashlaydi
     * 1. Ariza tegishli hududiy bo'limga tushadi va hududiy bo'lim inspektor biriktiradi
     * 2. Inspektor xicho ga borib xodimlarni attestatsiyadan o'tkazadi
     *
     *
     *
     *
     *
     */
}
