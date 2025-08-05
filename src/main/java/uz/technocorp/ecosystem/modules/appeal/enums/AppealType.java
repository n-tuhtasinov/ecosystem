package uz.technocorp.ecosystem.modules.appeal.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
@AllArgsConstructor
public enum AppealType {

    // Xavfli Ishlab Chiqarish Obyektini ro‘yxatga olish, o'zgartirish va chiqarish
    REGISTER_HF("", "registerHf", "", "HF", ""),
    DEREGISTER_HF("", "deregisterHf", "", "HF", ""),
    MODIFY_HF("", "hf", "modifyHf", "HF", ""),

    //Crane
    REGISTER_CRANE("", "registerEquipment", "P", "EQUIPMENT", "CRANE"),
    DEREGISTER_CRANE("", "deregisterEquipment", "P", "EQUIPMENT", "CRANE"),
    RE_REGISTER_CRANE("", "reRegisterEquipment", "P", "EQUIPMENT", "CRANE"),

    //Container - bosim ostida ishlovchi idish
    REGISTER_CONTAINER("", "registerEquipment", "A", "EQUIPMENT", "CONTAINER"),
    DEREGISTER_CONTAINER("", "deregisterEquipment", "A", "EQUIPMENT", "CONTAINER"),
    RE_REGISTER_CONTAINER("", "reRegisterEquipment", "A", "EQUIPMENT", "CONTAINER"),

    //Boiler - bug'qozon
    REGISTER_BOILER("", "registerEquipment", "K", "EQUIPMENT", "BOILER"),
    DEREGISTER_BOILER("", "deregisterEquipment", "K", "EQUIPMENT", "BOILER"),
    RE_REGISTER_BOILER("", "reRegisterEquipment", "K", "EQUIPMENT", "BOILER"),

    //Elevator - lift
    REGISTER_ELEVATOR("", "registerEquipment", "L", "EQUIPMENT", "ELEVATOR"),
    DEREGISTER_ELEVATOR("", "deregisterEquipment", "L", "EQUIPMENT", "ELEVATOR"),
    RE_REGISTER_ELEVATOR("", "reRegisterEquipment", "L", "EQUIPMENT", "ELEVATOR"),

    //Escalator
    REGISTER_ESCALATOR("", "registerEquipment", "E", "EQUIPMENT", "ESCALATOR"),
    DEREGISTER_ESCALATOR("", "deregisterEquipment", "E", "EQUIPMENT", "ESCALATOR"),
    RE_REGISTER_ESCALATOR("", "reRegisterEquipment", "E", "EQUIPMENT", "ESCALATOR"),

    //Cableway - osma qarqonli yuruvchi yo'l
    REGISTER_CABLEWAY("", "registerEquipment", "KD", "EQUIPMENT", "CABLEWAY"),
    DEREGISTER_CABLEWAY("", "deregisterEquipment", "KD", "EQUIPMENT", "CABLEWAY"),
    RE_REGISTER_CABLEWAY("", "reRegisterEquipment", "KD", "EQUIPMENT", "CABLEWAY"),

    //hoist - yuk ko'targich
    REGISTER_HOIST("", "registerEquipment", "V", "EQUIPMENT", "HOIST"),
    DEREGISTER_HOIST("", "deregisterEquipment", "V", "EQUIPMENT", "HOIST"),
    RE_REGISTER_HOIST("", "reRegisterEquipment", "V", "EQUIPMENT", "HOIST"),

    //pipeline - quvur
    REGISTER_PIPELINE("", "registerEquipment", "T", "EQUIPMENT", "PIPELINE"),
    DEREGISTER_PIPELINE("", "deregisterEquipment", "T", "EQUIPMENT", "PIPELINE"),
    RE_REGISTER_PIPELINE("", "reRegisterEquipment", "T", "EQUIPMENT", "PIPELINE"),

    //attraction_passport - Attraksion pasporti
    REGISTER_ATTRACTION_PASSPORT("", "registerAttractionPassport", "AT", "EQUIPMENT", "ATTRACTION_PASSPORT"),
    DEREGISTER_ATTRACTION_PASSPORT("", "deregisterAttractionPassport", "AT", "EQUIPMENT", "ATTRACTION_PASSPORT"),
    RE_REGISTER_ATTRACTION_PASSPORT("", "reRegisterAttractionPassport", "AT", "EQUIPMENT", "ATTRACTION_PASSPORT"),

    //attraction - Attraksion
    REGISTER_ATTRACTION("", "registerEquipment", "ADR", "EQUIPMENT", "ATTRACTION_PASSPORT"),
    DEREGISTER_ATTRACTION("", "deregisterEquipment", "ADR", "EQUIPMENT", "ATTRACTION_PASSPORT"),
    RE_REGISTER_ATTRACTION("", "reRegisterEquipment", "ADR", "EQUIPMENT", "ATTRACTION_PASSPORT"),

    //Chemical container - bosim ostida ishlovchi idish (kimyoviy)
    REGISTER_CHEMICAL_CONTAINER("", "registerEquipment", "XA", "EQUIPMENT", "CHEMICAL_CONTAINER"),
    DEREGISTER_CHEMICAL_CONTAINER("", "deregisterEquipment", "XA", "EQUIPMENT", "CHEMICAL_CONTAINER"),
    RE_REGISTER_CHEMICAL_CONTAINER("", "reRegisterEquipment", "XA", "EQUIPMENT", "CHEMICAL_CONTAINER"),

    //Heat pipe - bug' va issiqsuv quvuri
    REGISTER_HEAT_PIPELINE("", "registerEquipment", "PAX", "EQUIPMENT", "HEAT_PIPELINE"),
    DEREGISTER_HEAT_PIPELINE("", "deregisterEquipment", "PAX", "EQUIPMENT", "HEAT_PIPELINE"),
    RE_REGISTER_HEAT_PIPELINE("", "reRegisterEquipment", "PAX", "EQUIPMENT", "HEAT_PIPELINE"),

    //Boiler utilizer - qozon utilizator
    REGISTER_BOILER_UTILIZER("", "registerEquipment", "KC", "EQUIPMENT", "BOILER_UTILIZER"),
    DEREGISTER_BOILER_UTILIZER("", "deregisterEquipment", "KC", "EQUIPMENT", "BOILER_UTILIZER"),
    RE_REGISTER_BOILER_UTILIZER("", "reRegisterEquipment", "KC", "EQUIPMENT", "BOILER_UTILIZER"),

    //LPG container (Liquefied Petroleum Gas) - bosim ostida ishlovchi idish (SUG)
    REGISTER_LPG_CONTAINER("", "registerEquipment", "AG", "EQUIPMENT", "LPG_CONTAINER"),
    DEREGISTER_LPG_CONTAINER("", "deregisterEquipment", "AG", "EQUIPMENT", "LPG_CONTAINER"),
    RE_REGISTER_LPG_CONTAINER("", "reRegisterEquipment", "AG", "EQUIPMENT", "LPG_CONTAINER"),

    //LPG powered (Liquefied Petroleum Gas) - SUG bilan ishlovchi qurilmalar
    REGISTER_LPG_POWERED("", "registerEquipment", "TG", "EQUIPMENT", "LPG_POWERED"),
    DEREGISTER_LPG_POWERED("", "deregisterEquipment", "TG", "EQUIPMENT", "LPG_POWERED"),
    RE_REGISTER_LPG_POWERED("", "reRegisterEquipment", "TG", "EQUIPMENT", "LPG_POWERED"),

    // Xavfli ishlab chiqarish obyektlari xodimlarini attestatsiyadan o‘tkazish
    CERTIFY_HF_EMPLOYEE("", "", "", "", ""),

    // Ekspert tashkilotini akkreditatsiya qilish
    ACCREDIT_EXPERT_ORGANIZATION("", "accreditExpertOrganization", "", "ACCREDITATION", ""),
    RE_ACCREDIT_EXPERT_ORGANIZATION("", "reAccreditExpertOrganization", "", "ACCREDITATION", ""),
    EXPEND_ACCREDITATION_SCOPE("", "expendAccreditExpertOrganization", "", "ACCREDITATION", ""),

    // Sanoat xavfsizligi bo‘yicha ekspertiza xulosalari
    REGISTER_EXPERTISE_CONCLUSION("", "registerExpertiseConclusion", "", "ACCREDITATION", ""),
//    DEREGISTER_EXPERTISE_CONCLUSION("", "", ""),

    // Litsenziya va ruxsatnomalar
    OBTAIN_LICENSE("", "", "", "PERMITS", ""),
    OBTAIN_PERMIT("", "", "", "PERMITS", ""),
    OBTAIN_CONCLUSION("", "", "", "PERMITS", ""),

    // Xavfli ishlab chiqarish obyektlari kadastr pasporti
    REGISTER_CADASTRE_PASSPORT("", "registerCadastrePassport", "", "CADASTRE", ""),
    DEREGISTER_CADASTRE_PASSPORT("", "", "", "CADASTRE", ""),

    // Sanoat xavfsizligi deklaratsiyasi
    REGISTER_DECLARATION("", "registerDeclaration", "", "CADASTRE", ""),
    DEREGISTER_DECLARATION("", "", "", "CADASTRE", ""),

    //IRS (Ionizing Radiation Source ) - INM ro'yhatga olish, qabul qilib olish va berish
    REGISTER_IRS("", "registerIrs", "", "IRS", ""),
    ACCEPT_IRS("", "irs", "", "IRS", ""),
    TRANSFER_IRS("", "irs", "", "IRS", ""),

    // Attestation
    ATTESTATION_COMMITTEE("", "registerAttestation", "", "ATTESTATION_COMMITTEE", ""),
    ATTESTATION_REGIONAL("", "registerAttestation", "", "ATTESTATION_REGIONAL", "");

    public final String label;
    public final String sort;
    public final String symbol;
    public final String direction;
    public final String type;
    private static final Map<String, List<AppealType>> LIST_BY_DIRECTION;

    static {
        LIST_BY_DIRECTION = Arrays.stream(AppealType.values()).collect(Collectors.groupingBy(appealType -> appealType.direction));
    }

    public static List<AppealType> getEnumListByDirection(String direction) {
        if (direction == null || direction.isEmpty()) {
            return List.of();
        }
        return LIST_BY_DIRECTION.getOrDefault(direction, List.of());
    }

    public static AppealType findByTypeAndSort(String type, String sort) {
        return Stream.of(AppealType.values())
                .filter(a -> type != null && type.equals(a.type) && a.sort != null && a.sort.equals(sort))
                .findFirst().orElse(null);
    }
}
