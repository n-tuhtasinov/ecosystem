package uz.technocorp.ecosystem.modules.appeal.enums;

import lombok.AllArgsConstructor;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
@AllArgsConstructor
public enum AppealType {

    // Xavfli Ishlab Chiqarish Obyektini ro‘yxatga olish, o'zgartirish va chiqarish
    REGISTER_HF("", "registerHf", "", "HF"),
    DEREGISTER_HF("", "deregisterHf", "", "HF"),
    MODIFY_HF("", "hf", "modifyHf", "HF"),

    //Crane
    REGISTER_CRANE("", "registerEquipment", "P", "EQUIPMENT"),
    DEREGISTER_CRANE("", "deregisterEquipment", "P", "EQUIPMENT"),
    RE_REGISTER_CRANE("", "reRegisterEquipment", "P", "EQUIPMENT"),

    //Container - bosim ostida ishlovchi idish
    REGISTER_CONTAINER("", "registerEquipment", "A", "EQUIPMENT"),
    DEREGISTER_CONTAINER("", "deregisterEquipment", "A", "EQUIPMENT"),
    RE_REGISTER_CONTAINER("", "reRegisterEquipment", "A", "EQUIPMENT"),

    //Boiler - bug'qozon
    REGISTER_BOILER("", "registerEquipment", "K", "EQUIPMENT"),
    DEREGISTER_BOILER("", "deregisterEquipment", "K", "EQUIPMENT"),
    RE_REGISTER_BOILER("", "reRegisterEquipment", "K", "EQUIPMENT"),

    //Elevator - lift
    REGISTER_ELEVATOR("", "registerEquipment", "L", "EQUIPMENT"),
    DEREGISTER_ELEVATOR("", "deregisterEquipment", "L", "EQUIPMENT"),
    RE_REGISTER_ELEVATOR("", "reRegisterEquipment", "L", "EQUIPMENT"),

    //Escalator
    REGISTER_ESCALATOR("", "registerEquipment", "E", "EQUIPMENT"),
    DEREGISTER_ESCALATOR("", "deregisterEquipment", "E", "EQUIPMENT"),
    RE_REGISTER_ESCALATOR("", "reRegisterEquipment", "E", "EQUIPMENT"),

    //Cableway - osma qarqonli yuruvchi yo'l
    REGISTER_CABLEWAY("", "registerEquipment", "KD", "EQUIPMENT"),
    DEREGISTER_CABLEWAY("", "deregisterEquipment", "KD", "EQUIPMENT"),
    RE_REGISTER_CABLEWAY("", "reRegisterEquipment", "KD", "EQUIPMENT"),

    //hoist - yuk ko'targich
    REGISTER_HOIST("", "registerEquipment", "V", "EQUIPMENT"),
    DEREGISTER_HOIST("", "deregisterEquipment", "V", "EQUIPMENT"),
    RE_REGISTER_HOIST("", "reRegisterEquipment", "V", "EQUIPMENT"),

    //pipeline - quvur
    REGISTER_PIPELINE("", "registerEquipment", "T", "EQUIPMENT"),
    DEREGISTER_PIPELINE("", "deregisterEquipment", "T", "EQUIPMENT"),
    RE_REGISTER_PIPELINE("", "reRegisterEquipment", "T", "EQUIPMENT"),

    //attraction_passport - Attraksion pasporti
    REGISTER_ATTRACTION_PASSPORT("", "registerAttractionPassport", "AT", "EQUIPMENT"),
    DEREGISTER_ATTRACTION_PASSPORT("", "deregisterAttractionPassport", "AT", "EQUIPMENT"),
    RE_REGISTER_ATTRACTION_PASSPORT("", "reRegisterAttractionPassport", "AT", "EQUIPMENT"),

    //attraction - Attraksion
    REGISTER_ATTRACTION("", "registerEquipment", "ADR", "EQUIPMENT"),
    DEREGISTER_ATTRACTION("", "deregisterEquipment", "ADR", "EQUIPMENT"),
    RE_REGISTER_ATTRACTION("", "reRegisterEquipment", "ADR", "EQUIPMENT"),

    //Chemical container - bosim ostida ishlovchi idish (kimyoviy)
    REGISTER_CHEMICAL_CONTAINER("", "registerEquipment", "XA", "EQUIPMENT"),
    DEREGISTER_CHEMICAL_CONTAINER("", "deregisterEquipment", "XA", "EQUIPMENT"),
    RE_REGISTER_CHEMICAL_CONTAINER("", "reRegisterEquipment", "XA", "EQUIPMENT"),

    //Heat pipe - bug' va issiqsuv quvuri
    REGISTER_HEAT_PIPELINE("", "registerEquipment", "PAX", "EQUIPMENT"),
    DEREGISTER_HEAT_PIPELINE("", "deregisterEquipment", "PAX", "EQUIPMENT"),
    RE_REGISTER_HEAT_PIPELINE("", "reRegisterEquipment", "PAX", "EQUIPMENT"),

    //Boiler utilizer - qozon utilizator
    REGISTER_BOILER_UTILIZER("", "registerEquipment", "KC", "EQUIPMENT"),
    DEREGISTER_BOILER_UTILIZER("", "deregisterEquipment", "KC", "EQUIPMENT"),
    RE_REGISTER_BOILER_UTILIZER("", "reRegisterEquipment", "KC", "EQUIPMENT"),

    //LPG container (Liquefied Petroleum Gas) - bosim ostida ishlovchi idish (SUG)
    REGISTER_LPG_CONTAINER("", "registerEquipment", "AG", "EQUIPMENT"),
    DEREGISTER_LPG_CONTAINER("", "deregisterEquipment", "AG", "EQUIPMENT"),
    RE_REGISTER_LPG_CONTAINER("", "reRegisterEquipment", "AG", "EQUIPMENT"),

    //LPG powered (Liquefied Petroleum Gas) - SUG bilan ishlovchi qurilmalar
    REGISTER_LPG_POWERED("", "registerEquipment", "TG", "EQUIPMENT"),
    DEREGISTER_LPG_POWERED("", "deregisterEquipment", "TG", "EQUIPMENT"),
    RE_REGISTER_LPG_POWERED("", "reRegisterEquipment", "TG", "EQUIPMENT"),

    // Xavfli ishlab chiqarish obyektlari xodimlarini attestatsiyadan o‘tkazish
    CERTIFY_HF_EMPLOYEE("", "", "", ""),

    // Ekspert tashkilotini akkreditatsiya qilish
    ACCREDIT_EXPERT_ORGANIZATION("", "accreditExpertOrganization", "", "ACCREDITATION"),
    RE_ACCREDIT_EXPERT_ORGANIZATION("", "reAccreditExpertOrganization", "", "ACCREDITATION"),
    EXPEND_ACCREDITATION_SCOPE("", "expendAccreditExpertOrganization", "", "ACCREDITATION"),

    // Sanoat xavfsizligi bo‘yicha ekspertiza xulosalari
    REGISTER_EXPERTISE_CONCLUSION("", "registerExpertiseConclusion", "", "ACCREDITATION"),
//    DEREGISTER_EXPERTISE_CONCLUSION("", "", ""),

    // Litsenziya va ruxsatnomalar
    OBTAIN_LICENSE("", "", "", "PERMITS"),
    OBTAIN_PERMIT("", "", "", "PERMITS"),
    OBTAIN_CONCLUSION("", "", "", "PERMITS"),

    // Xavfli ishlab chiqarish obyektlari kadastr pasporti
    REGISTER_HF_CADASTRE_PASSPORT("", "", "", "CADASTRE"),
    DEREGISTER_HF_CADASTRE_PASSPORT("", "", "", "CADASTRE"),

    // Sanoat xavfsizligi deklaratsiyasi
    REGISTER_DECLARATION("", "", "", "CADASTRE"),
    DEREGISTER_DECLARATION("", "", "", "CADASTRE"),

    //IRS (Ionizing Radiation Source ) - INM ro'yhatga olish, qabul qilib olish va berish
    REGISTER_IRS("", "registerIrs", "", "IRS"),
    ACCEPT_IRS("", "irs", "", "IRS"),
    TRANSFER_IRS("", "irs", "", "IRS"),

    // Attestation
    ATTESTATION_COMMITTEE("", "", "","ATTESTATION"),
    ATTESTATION_REGIONAL("", "", "", "ATTESTATION");

    public final String label;
    public final String sort;
    public final String symbol;
    public final String direction;
    private static final Map<String, AppealType> BY_DIRECTION = new HashMap<>();

    static {
        for (AppealType value : AppealType.values()) {
            BY_DIRECTION.put(value.direction, value);
        }
    }

    public static AppealType getAppealTypeByDirection(String direction) {
        if (direction == null || direction.isBlank() || !BY_DIRECTION.containsKey(direction)) {
            return null;
        }
        return BY_DIRECTION.get(direction);
    }
}
