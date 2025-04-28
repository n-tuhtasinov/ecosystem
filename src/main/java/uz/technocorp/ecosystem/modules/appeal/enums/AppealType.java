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
    MODIFY_HF,

    //Crane
    REGISTER_CRANE,
    DEREGISTER_CRANE,
    RE_REGISTER_CRANE,

    //Container - bosim ostida ishlovchi idish
    REGISTER_CONTAINER,
    DEREGISTER_CONTAINER,
    RE_REGISTER_CONTAINER,

    //Boiler - bug'qozon
    REGISTER_BOILER,
    DEREGISTER_BOILER,
    RE_REGISTER_BOILER,

    //Elevator - lift
    REGISTER_ELEVATOR,
    DEREGISTER_ELEVATOR,
    RE_REGISTER_ELEVATOR,

    //Escalator
    REGISTER_ESCALATOR,
    DEREGISTER_ESCALATOR,
    RE_REGISTER_ESCALATOR,

    //Cableway - osma qarqonli yuruvchi yo'l
    REGISTER_CABLEWAY,
    DEREGISTER_CABLEWAY,
    RE_REGISTER_CABLEWAY,

    //hoist - yuk ko'targich
    REGISTER_HOIST,
    DEREGISTER_HOIST,
    RE_REGISTER_HOIST,

    //pipeline - quvur
    REGISTER_PIPELINE,
    DEREGISTER_PIPELINE,
    RE_REGISTER_PIPELINE,

    //Chemical container - bosim ostida ishlovchi idish (kimyoviy)
    REGISTER_CHEMICAL_CONTAINER,
    DEREGISTER_CHEMICAL_CONTAINER,
    RE_REGISTER_CHEMICAL_CONTAINER,

    //Heat pipe - bug' va issiqsuv quvuri
    REGISTER_HEAT_PIPELINE,
    DEREGISTER_HEAT_PIPELINE,
    RE_REGISTER_HEAT_PIPELINE,


    //Boiler utilizer - qozon utilizator
    REGISTER_BOILER_UTILIZER,
    DEREGISTER_BOILER_UTILIZER,
    RE_REGISTER_BOILER_UTILIZER,

    //LPG container (Liquefied Petroleum Gas) - bosim ostida ishlovchi idish (SUG)
    REGISTER_LPG_CONTAINER,
    DEREGISTER_LPG_CONTAINER,
    RE_REGISTER_LPG_CONTAINER,

    //LPG powered (Liquefied Petroleum Gas) - SUG bilan ishlovchi qurilmalar
    REGISTER_LPG_POWERED,
    DEREGISTER_LPG_POWERED,
    RE_REGISTER_LPG_POWERED,

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
