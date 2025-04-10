package uz.technocorp.ecosystem.modules.appeal.enums;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
public enum AppealType {

    // Xavfli Ishlab Chiqarish Obyektini ro‘yxatga olish va chiqarish
    REGISTER_HF,
    DEREGISTER_HF,

    // Qurilmalar
    REGISTER_EQUIPMENT,
    DEREGISTER_EQUIPMENT,
    RE_REGISTER_EQUIPMENT,

    // Xavfli ishlab chiqarish obyektlari xodimlarini attestatsiyadan o‘tkazish
    CERTIFY_HF_EMPLOYEE,

    // Ekspert tashkilotini akkreditatsiya qilish
    ACCREDIT_EXPERT_ORGANIZATION,
    EXPEND_ACCREDITATION_SCOPE,

    // Sanoat xavfsizligi bo‘yicha ekspertiza xulosalari
    REGISTER_EXPERTISE_CONCLUSION,
    DEREGISTER_EXPERTISE_CONCLUSION,

    // Litsenziya va ruxsatnomalar
    OBTAIN_LICENSE,
    OBTAIN_PERMIT,
    OBTAIN_CONCLUSION,

    // Xavfli ishlab chiqarish obyektlari kadastr pasporti
    REGISTER_HF_CADASTRE_PASSPORT,
    DEREGISTER_HF_CADASTRE_PASSPORT,

    // Sanoat xavfsizligi deklaratsiyasi
    REGISTER_DECLARATION,
    DEREGISTER_DECLARATION,

    // INM - IRS (Ionizing Radiation Source )
    REGISTER_IRS,
    ACCEPT_IRS,
    TRANSFER_IRS,
    }
