package uz.technocorp.ecosystem.modules.appeal.enums;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 24.02.2025
 * @since v1.0
 */
public enum AppealStatus {
    NEW,
    IN_PROCESS, //inspektor biriktirilganda (jarayonda)
    IN_AGREEMENT, // hududiy bo'lim boshlig'iga o'tganda (kelishishda)
    IN_APPROVAL, // qo'mita mas'ul hodimiga o'tganda (tasdiqlashda)
    COMPLETED, // qo'mita mas'ul hodimi tasdiqlaganda (yakunlangan)
    CANCELED, // ariza tushishi bilan kamchilik yoki xatolik sababli qaytarilsa (qaytarilgan)
    REJECTED //arizani o'rganish davomida inspektor tomonidan salbiy xulosa berilganda(rad etilgan)
}
