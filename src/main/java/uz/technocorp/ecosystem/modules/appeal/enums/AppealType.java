package uz.technocorp.ecosystem.modules.appeal.enums;

import lombok.AllArgsConstructor;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;

import java.util.Arrays;

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
    REGISTER_HF("", "registerHf", "", "hf"),
    DEREGISTER_HF("", "deregisterHf", "", "hf"),
    MODIFY_HF("", "hf", "modifyHf", "hf"),

    //Crane
    REGISTER_CRANE("", "registerEquipment", "P", ""),
    DEREGISTER_CRANE("", "deregisterEquipment", "P", ""),
    RE_REGISTER_CRANE("", "reRegisterEquipment", "P", ""),

    //Container - bosim ostida ishlovchi idish
    REGISTER_CONTAINER("", "registerEquipment", "A", ""),
    DEREGISTER_CONTAINER("", "deregisterEquipment", "A", ""),
    RE_REGISTER_CONTAINER("", "reRegisterEquipment", "A", ""),

    //Boiler - bug'qozon
    REGISTER_BOILER("", "registerEquipment", "K", ""),
    DEREGISTER_BOILER("", "deregisterEquipment", "K", ""),
    RE_REGISTER_BOILER("", "reRegisterEquipment", "K", ""),

    //Elevator - lift
    REGISTER_ELEVATOR("", "registerEquipment", "L", ""),
    DEREGISTER_ELEVATOR("", "deregisterEquipment", "L", ""),
    RE_REGISTER_ELEVATOR("", "reRegisterEquipment", "L", ""),

    //Escalator
    REGISTER_ESCALATOR("", "registerEquipment", "E", ""),
    DEREGISTER_ESCALATOR("", "deregisterEquipment", "E", ""),
    RE_REGISTER_ESCALATOR("", "reRegisterEquipment", "E", ""),

    //Cableway - osma qarqonli yuruvchi yo'l
    REGISTER_CABLEWAY("", "registerEquipment", "KD", ""),
    DEREGISTER_CABLEWAY("", "deregisterEquipment", "KD", ""),
    RE_REGISTER_CABLEWAY("", "reRegisterEquipment", "KD", ""),

    //hoist - yuk ko'targich
    REGISTER_HOIST("", "registerEquipment", "V", ""),
    DEREGISTER_HOIST("", "deregisterEquipment", "V", ""),
    RE_REGISTER_HOIST("", "reRegisterEquipment", "V", ""),

    //pipeline - quvur
    REGISTER_PIPELINE("", "registerEquipment", "T", ""),
    DEREGISTER_PIPELINE("", "deregisterEquipment", "T", ""),
    RE_REGISTER_PIPELINE("", "reRegisterEquipment", "T", ""),

    //attraction_passport - Attraksion pasporti
    REGISTER_ATTRACTION_PASSPORT("", "registerAttractionPassport", "AT", ""),
    DEREGISTER_ATTRACTION_PASSPORT("", "deregisterAttractionPassport", "AT", ""),
    RE_REGISTER_ATTRACTION_PASSPORT("", "reRegisterAttractionPassport", "AT", ""),

    //attraction - Attraksion
    REGISTER_ATTRACTION("", "registerEquipment", "ADR", ""),
    DEREGISTER_ATTRACTION("", "deregisterEquipment", "ADR", ""),
    RE_REGISTER_ATTRACTION("", "reRegisterEquipment", "ADR", ""),

    //Chemical container - bosim ostida ishlovchi idish (kimyoviy)
    REGISTER_CHEMICAL_CONTAINER("", "registerEquipment", "XA", ""),
    DEREGISTER_CHEMICAL_CONTAINER("", "deregisterEquipment", "XA", ""),
    RE_REGISTER_CHEMICAL_CONTAINER("", "reRegisterEquipment", "XA", ""),

    //Heat pipe - bug' va issiqsuv quvuri
    REGISTER_HEAT_PIPELINE("", "registerEquipment", "PAX", ""),
    DEREGISTER_HEAT_PIPELINE("", "deregisterEquipment", "PAX", ""),
    RE_REGISTER_HEAT_PIPELINE("", "reRegisterEquipment", "PAX", ""),

    //Boiler utilizer - qozon utilizator
    REGISTER_BOILER_UTILIZER("", "registerEquipment", "KC", ""),
    DEREGISTER_BOILER_UTILIZER("", "deregisterEquipment", "KC", ""),
    RE_REGISTER_BOILER_UTILIZER("", "reRegisterEquipment", "KC", ""),

    //LPG container (Liquefied Petroleum Gas) - bosim ostida ishlovchi idish (SUG)
    REGISTER_LPG_CONTAINER("", "registerEquipment", "AG", ""),
    DEREGISTER_LPG_CONTAINER("", "deregisterEquipment", "AG", ""),
    RE_REGISTER_LPG_CONTAINER("", "reRegisterEquipment", "AG", ""),

    //LPG powered (Liquefied Petroleum Gas) - SUG bilan ishlovchi qurilmalar
    REGISTER_LPG_POWERED("", "registerEquipment", "TG", ""),
    DEREGISTER_LPG_POWERED("", "deregisterEquipment", "TG", ""),
    RE_REGISTER_LPG_POWERED("", "reRegisterEquipment", "TG", ""),

    // Xavfli ishlab chiqarish obyektlari xodimlarini attestatsiyadan o‘tkazish
    CERTIFY_HF_EMPLOYEE("", "", "", ""),

    // Ekspert tashkilotini akkreditatsiya qilish
    ACCREDIT_EXPERT_ORGANIZATION("", "accreditExpertOrganization", "", "accreditation"),
    RE_ACCREDIT_EXPERT_ORGANIZATION("", "reAccreditExpertOrganization", "", "accreditation"),
    EXPEND_ACCREDITATION_SCOPE("", "expendAccreditExpertOrganization", "", "accreditation"),

    // Sanoat xavfsizligi bo‘yicha ekspertiza xulosalari
    REGISTER_EXPERTISE_CONCLUSION("", "registerExpertiseConclusion", "", "accreditation"),
//    DEREGISTER_EXPERTISE_CONCLUSION("", "", ""),

    // Litsenziya va ruxsatnomalar
    OBTAIN_LICENSE("", "", "", ""),
    OBTAIN_PERMIT("", "", "", ""),
    OBTAIN_CONCLUSION("", "", "", ""),

    // Xavfli ishlab chiqarish obyektlari kadastr pasporti
    REGISTER_HF_CADASTRE_PASSPORT("", "", "", ""),
    DEREGISTER_HF_CADASTRE_PASSPORT("", "", "", ""),

    // Sanoat xavfsizligi deklaratsiyasi
    REGISTER_DECLARATION("", "", "", ""),
    DEREGISTER_DECLARATION("", "", "", ""),

    //IRS (Ionizing Radiation Source ) - INM ro'yhatga olish, qabul qilib olish va berish
    REGISTER_IRS("", "registerIrs", "", "irs"),
    ACCEPT_IRS("", "irs", "", "irs"),
    TRANSFER_IRS("", "irs", "", "irs");

    public final String label;
    public final String sort;
    public final String symbol;
    public final String direction;

    public static AppealType getAppealTypeByDirection(String direction) {
        if (direction == null || direction.isEmpty()) {
            return null;
        }
        return Arrays.stream(AppealType.values())
                .filter(appealType -> appealType.direction.equals(direction))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Ariza turi", "Direction", direction));

    }
}
