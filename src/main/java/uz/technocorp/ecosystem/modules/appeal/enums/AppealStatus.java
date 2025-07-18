package uz.technocorp.ecosystem.modules.appeal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.02.2025
 * @since v1.0
 */
@Getter
@AllArgsConstructor
public enum AppealStatus {
    NEW("Yaratdi"),
    IN_PROCESS("Jarayonga o'tkazdi"), //inspektor biriktirilganda (jarayonda)
    IN_AGREEMENT("Kelishishga o'tkazdi"), // hududiy bo'lim boshlig'iga o'tganda (kelishishda)
    IN_APPROVAL("Tasdiqladi"), // qo'mita mas'ul hodimiga o'tganda (tasdiqlashda)
    COMPLETED("Yakunladi"), // qo'mita mas'ul hodimi tasdiqlaganda (yakunlangan)
    CANCELED("Qaytardi"), // ariza tushishi bilan kamchilik yoki xatolik sababli qaytarilsa (qaytarilgan)
    REJECTED("Rad etdi"); //arizani o'rganish davomida inspektor tomonidan salbiy xulosa berilganda(rad etilgan)

    private final String label;
}
