package uz.technocorp.ecosystem.modules.appeal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
@AllArgsConstructor
public enum AppealType {
    // TODO
    // Xavfli Ishlab Chiqarish Obyektini ro‘yxatga olish, o'zgartirish va chiqarish
    REGISTER_HF("", "hf", ""),
    DEREGISTER_HF("", "hf", ""),
    MODIFY_HF("", "hf", ""),

    //Crane
    REGISTER_CRANE("", "equipment", "P"),
    DEREGISTER_CRANE("", "equipment", "P"),
    RE_REGISTER_CRANE("", "equipment", "P"),

    //Container - bosim ostida ishlovchi idish
    REGISTER_CONTAINER("", "equipment", "A"),
    DEREGISTER_CONTAINER("", "equipment", "A"),
    RE_REGISTER_CONTAINER("", "equipment", "A"),

    //Boiler - bug'qozon
    REGISTER_BOILER("", "equipment", "K"),
    DEREGISTER_BOILER("", "equipment", "K"),
    RE_REGISTER_BOILER("", "equipment", "K"),

    //Elevator - lift
    REGISTER_ELEVATOR("", "equipment", "L"),
    DEREGISTER_ELEVATOR("", "equipment", "L"),
    RE_REGISTER_ELEVATOR("", "equipment", "L"),

    //Escalator
    REGISTER_ESCALATOR("", "equipment", "E"),
    DEREGISTER_ESCALATOR("", "equipment", "E"),
    RE_REGISTER_ESCALATOR("", "equipment", "E"),

    //Cableway - osma qarqonli yuruvchi yo'l
    REGISTER_CABLEWAY("", "equipment", "KD"),
    DEREGISTER_CABLEWAY("", "equipment", "KD"),
    RE_REGISTER_CABLEWAY("", "equipment", "KD"),

    //hoist - yuk ko'targich
    REGISTER_HOIST("", "equipment", "V"),
    DEREGISTER_HOIST("", "equipment", "V"),
    RE_REGISTER_HOIST("", "equipment", "V"),

    //pipeline - quvur
    REGISTER_PIPELINE("", "equipment", "T"),
    DEREGISTER_PIPELINE("", "equipment", "T"),
    RE_REGISTER_PIPELINE("", "equipment", "T"),

    //attraction_passport - Attraksion pasporti
    REGISTER_ATTRACTION_PASSPORT("", "equipment", "AT"),
    DEREGISTER_ATTRACTION_PASSPORT("", "equipment", "AT"),
    RE_REGISTER_ATTRACTION_PASSPORT("", "equipment", "AT"),

    //attraction - Attraksion
    REGISTER_ATTRACTION("", "equipment", "ADR"),
    DEREGISTER_ATTRACTION("", "equipment", "ADR"),
    RE_REGISTER_ATTRACTION("", "equipment", "ADR"),

    //Chemical container - bosim ostida ishlovchi idish (kimyoviy)
    REGISTER_CHEMICAL_CONTAINER("", "equipment", "XA"),
    DEREGISTER_CHEMICAL_CONTAINER("", "equipment", "XA"),
    RE_REGISTER_CHEMICAL_CONTAINER("", "equipment", "XA"),

    //Heat pipe - bug' va issiqsuv quvuri
    REGISTER_HEAT_PIPELINE("", "equipment", "PAX"),
    DEREGISTER_HEAT_PIPELINE("", "equipment", "PAX"),
    RE_REGISTER_HEAT_PIPELINE("", "equipment", "PAX"),

    //Boiler utilizer - qozon utilizator
    REGISTER_BOILER_UTILIZER("", "equipment", "KC"),
    DEREGISTER_BOILER_UTILIZER("", "equipment", "KC"),
    RE_REGISTER_BOILER_UTILIZER("", "equipment", "KC"),

    //LPG container (Liquefied Petroleum Gas) - bosim ostida ishlovchi idish (SUG)
    REGISTER_LPG_CONTAINER("", "equipment", "AG"),
    DEREGISTER_LPG_CONTAINER("", "equipment", "AG"),
    RE_REGISTER_LPG_CONTAINER("", "equipment", "AG"),

    //LPG powered (Liquefied Petroleum Gas) - SUG bilan ishlovchi qurilmalar
    REGISTER_LPG_POWERED("", "equipment", "TG"),
    DEREGISTER_LPG_POWERED("", "equipment", "TG"),
    RE_REGISTER_LPG_POWERED("", "equipment", "TG"),

    // Xavfli ishlab chiqarish obyektlari xodimlarini attestatsiyadan o‘tkazish
    CERTIFY_HF_EMPLOYEE("", "", ""),

    // Ekspert tashkilotini akkreditatsiya qilish
    ACCREDIT_EXPERT_ORGANIZATION("", "", ""),
    EXPEND_ACCREDITATION_SCOPE("", "", ""),

    // Sanoat xavfsizligi bo‘yicha ekspertiza xulosalari
    REGISTER_EXPERTISE_CONCLUSION("", "", ""),
    DEREGISTER_EXPERTISE_CONCLUSION("", "", ""),

    // Litsenziya va ruxsatnomalar
    OBTAIN_LICENSE("", "", ""),
    OBTAIN_PERMIT("", "", ""),
    OBTAIN_CONCLUSION("", "", ""),

    // Xavfli ishlab chiqarish obyektlari kadastr pasporti
    REGISTER_HF_CADASTRE_PASSPORT("", "", ""),
    DEREGISTER_HF_CADASTRE_PASSPORT("", "", ""),

    // Sanoat xavfsizligi deklaratsiyasi
    REGISTER_DECLARATION("", "", ""),
    DEREGISTER_DECLARATION("", "", ""),

    //IRS (Ionizing Radiation Source ) - INM ro'yhatga olish, qabul qilib olish va berish
    REGISTER_IRS("", "irs", ""),
    ACCEPT_IRS("", "irs", ""),
    TRANSFER_IRS("", "irs", "");

    public final String label;
    public final String sort;
    public final String symbol;
    }
