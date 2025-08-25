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
    REGISTER_HF("XIChOni ro‘yxatga olish", "registerHf", "", "HF", ""),
    DEREGISTER_HF("XIChOni ro‘yxatdan chiqarish", "deregisterHf", "", "HF", ""),
    MODIFY_HF("XIChO reestriga o‘zgartirish kiritish", "modifyHf", "", "HF", ""),

    //Crane
    REGISTER_CRANE("Kranni ro‘yxatga olish", "registerEquipment", "P", "EQUIPMENT", "CRANE"),
    DEREGISTER_CRANE("Kranni ro‘yxatdan chiqarish", "deregisterEquipment", "P", "EQUIPMENT", "CRANE"),
    RE_REGISTER_CRANE("Kranni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "P", "EQUIPMENT", "CRANE"),

    //Container - bosim ostida ishlovchi idish
    REGISTER_CONTAINER("Bosim ostida ishlovchi idishni ro‘yxatga olish", "registerEquipment", "A", "EQUIPMENT", "CONTAINER"),
    DEREGISTER_CONTAINER("Bosim ostida ishlovchi idishni ro‘yxatdan chiqarish", "deregisterEquipment", "A", "EQUIPMENT", "CONTAINER"),
    RE_REGISTER_CONTAINER("Bosim ostida ishlovchi idishni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "A", "EQUIPMENT", "CONTAINER"),

    //Boiler - bug'qozon
    REGISTER_BOILER("Bug‘qozonni ro‘yxatga olish", "registerEquipment", "K", "EQUIPMENT", "BOILER"),
    DEREGISTER_BOILER("Bug‘qozonni ro‘yxatdan chiqarish", "deregisterEquipment", "K", "EQUIPMENT", "BOILER"),
    RE_REGISTER_BOILER("Bug‘qozonni qayta ro‘yxatdan chiqarish", "reRegisterEquipment", "K", "EQUIPMENT", "BOILER"),

    //Elevator - lift
    REGISTER_ELEVATOR("Liftni ro‘yxatga olish", "registerEquipment", "L", "EQUIPMENT", "ELEVATOR"),
    DEREGISTER_ELEVATOR("Liftni ro‘yxatdan chiqarish", "deregisterEquipment", "L", "EQUIPMENT", "ELEVATOR"),
    RE_REGISTER_ELEVATOR("Liftni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "L", "EQUIPMENT", "ELEVATOR"),

    //Escalator
    REGISTER_ESCALATOR("Eskalatorni ro‘yxatga olish", "registerEquipment", "E", "EQUIPMENT", "ESCALATOR"),
    DEREGISTER_ESCALATOR("Eskalatorni ro‘yxatdan chiqarish", "deregisterEquipment", "E", "EQUIPMENT", "ESCALATOR"),
    RE_REGISTER_ESCALATOR("Eskalatorni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "E", "EQUIPMENT", "ESCALATOR"),

    //Cableway - osma qarqonli yuruvchi yo'l
    REGISTER_CABLEWAY("Osma arqonli yuruvchi yo‘lni ro‘yxatga olish", "registerEquipment", "KD", "EQUIPMENT", "CABLEWAY"),
    DEREGISTER_CABLEWAY("Osma arqonli yuruvchi yo‘lni ro‘yxatdan chiqarish", "deregisterEquipment", "KD", "EQUIPMENT", "CABLEWAY"),
    RE_REGISTER_CABLEWAY("Osma arqonli yuruvchi yo‘lni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "KD", "EQUIPMENT", "CABLEWAY"),

    //hoist - yuk ko'targich
    REGISTER_HOIST("Yuk ko‘targichni ro‘yxatga olish", "registerEquipment", "V", "EQUIPMENT", "HOIST"),
    DEREGISTER_HOIST("Yuk ko‘targichni ro‘yxatdan chiqarish", "deregisterEquipment", "V", "EQUIPMENT", "HOIST"),
    RE_REGISTER_HOIST("Yuk ko‘targichni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "V", "EQUIPMENT", "HOIST"),

    //pipeline - quvur
    REGISTER_PIPELINE("Quvurni ro‘yxatga olish", "registerEquipment", "T", "EQUIPMENT", "PIPELINE"),
    DEREGISTER_PIPELINE("Quvurni ro‘yxatdan chiqarish", "deregisterEquipment", "T", "EQUIPMENT", "PIPELINE"),
    RE_REGISTER_PIPELINE("Quvurni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "T", "EQUIPMENT", "PIPELINE"),

    //attraction_passport - Attraksion pasporti
    REGISTER_ATTRACTION_PASSPORT("Attraksion pasportini ro‘yxatga olish", "registerAttractionPassport", "AT", "EQUIPMENT", "ATTRACTION_PASSPORT"),
    DEREGISTER_ATTRACTION_PASSPORT("Attraksion pasportini ro‘yxatdan chiqarish", "deregisterAttractionPassport", "AT", "EQUIPMENT", "ATTRACTION_PASSPORT"),
    RE_REGISTER_ATTRACTION_PASSPORT("Attraksion pasportini qayta ro‘yxatdan o‘tkazish", "reRegisterAttractionPassport", "AT", "EQUIPMENT", "ATTRACTION_PASSPORT"),

    //attraction - Attraksion
    REGISTER_ATTRACTION("Attraksionni ro‘yxatga olish", "registerEquipment", "ADR", "EQUIPMENT", "ATTRACTION_PASSPORT"),
    DEREGISTER_ATTRACTION("Attraksionni ro‘yxatdan chiqarish", "deregisterEquipment", "ADR", "EQUIPMENT", "ATTRACTION_PASSPORT"),
    RE_REGISTER_ATTRACTION("Attraksionni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "ADR", "EQUIPMENT", "ATTRACTION_PASSPORT"),

    //Chemical container - bosim ostida ishlovchi idish (kimyoviy)
    REGISTER_CHEMICAL_CONTAINER("Bosim ostida ishlovchi idishlarni (kimyo)ro‘yxatga olish", "registerEquipment", "XA", "EQUIPMENT", "CHEMICAL_CONTAINER"),
    DEREGISTER_CHEMICAL_CONTAINER("Bosim ostida ishlovchi idishlarni (kimyo) ro‘yxatdan chiqarish", "deregisterEquipment", "XA", "EQUIPMENT", "CHEMICAL_CONTAINER"),
    RE_REGISTER_CHEMICAL_CONTAINER("Bosim ostida ishlovchi idishlarni (kimyo) qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "XA", "EQUIPMENT", "CHEMICAL_CONTAINER"),

    //Heat pipe - bug' va issiqsuv quvuri
    REGISTER_HEAT_PIPELINE("Bug‘ va issiq suv quvurlarini ro‘yxatga olish", "registerEquipment", "PAX", "EQUIPMENT", "HEAT_PIPELINE"),
    DEREGISTER_HEAT_PIPELINE("Bug‘ va issiq suv quvurlarini ro‘yxatdan chiqarish", "deregisterEquipment", "PAX", "EQUIPMENT", "HEAT_PIPELINE"),
    RE_REGISTER_HEAT_PIPELINE("Bug‘ va issiq suv quvurlarini qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "PAX", "EQUIPMENT", "HEAT_PIPELINE"),

    //Boiler utilizer - qozon utilizator
    REGISTER_BOILER_UTILIZER("Qozon utilizatorlarini ro‘yxatga olish", "registerEquipment", "KC", "EQUIPMENT", "BOILER_UTILIZER"),
    DEREGISTER_BOILER_UTILIZER("Qozon utilizatorlarini ro‘yxatdan chiqarish", "deregisterEquipment", "KC", "EQUIPMENT", "BOILER_UTILIZER"),
    RE_REGISTER_BOILER_UTILIZER("Qozon utilizatorlarini qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "KC", "EQUIPMENT", "BOILER_UTILIZER"),

    //LPG container (Liquefied Petroleum Gas) - bosim ostida ishlovchi idish (SUG)
    REGISTER_LPG_CONTAINER("Bosim ostida ishlovchi idishlarni (SUG)ro‘yxatga olish", "registerEquipment", "AG", "EQUIPMENT", "LPG_CONTAINER"),
    DEREGISTER_LPG_CONTAINER("Bosim ostida ishlovchi idishlarni (SUG) ro‘yxatdan chiqarish", "deregisterEquipment", "AG", "EQUIPMENT", "LPG_CONTAINER"),
    RE_REGISTER_LPG_CONTAINER("Bosim ostida ishlovchi idishlarni (SUG) qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "AG", "EQUIPMENT", "LPG_CONTAINER"),

    //LPG powered (Liquefied Petroleum Gas) - SUG bilan ishlovchi qurilmalar
    REGISTER_LPG_POWERED("Yiliga bitta uskuna uchun 100 000 va undan ortiq kubometr tabiiy va SUGdan yoqilg‘i sifatida foydalanuvchi texnik qurilmalarni ro‘yxatga olish", "registerEquipment", "TG", "EQUIPMENT", "LPG_POWERED"),
    DEREGISTER_LPG_POWERED("Yiliga bitta uskuna uchun 100 000 va undan ortiq kubometr tabiiy va SUGdan yoqilg‘i sifatida foydalanuvchi texnik qurilmalarni ro‘yxatdan chiqarish", "deregisterEquipment", "TG", "EQUIPMENT", "LPG_POWERED"),
    RE_REGISTER_LPG_POWERED("Yiliga bitta uskuna uchun 100 000 va undan ortiq kubometr tabiiy va SUGdan yoqilg‘i sifatida foydalanuvchi texnik qurilmalarni qayta ro‘yxatdan o‘tkazish", "reRegisterEquipment", "TG", "EQUIPMENT", "LPG_POWERED"),

    // Ekspert tashkilotini akkreditatsiya qilish
    ACCREDIT_EXPERT_ORGANIZATION("Ekspert tashkilotini akkreditasiyadan o‘tkazish", "accreditExpertOrganization", "", "ACCREDITATION", ""),
    RE_ACCREDIT_EXPERT_ORGANIZATION("Ekspert tashkilotini qayta akkreditasiyadan o‘tkazish", "reAccreditExpertOrganization", "", "ACCREDITATION", ""),
    EXPEND_ACCREDITATION_SCOPE("Ekspert tashkilotining akkreditasiya sohasini kengaytirish", "expendAccreditExpertOrganization", "", "ACCREDITATION", ""),

    // Sanoat xavfsizligi bo‘yicha ekspertiza xulosalari
    REGISTER_EXPERTISE_CONCLUSION("Sanoat xavfsizligi ekspertiza xulosalarini ro‘yxatga olish", "registerExpertiseConclusion", "", "ACCREDITATION", ""),

    // Litsenziya va ruxsatnomalar
//    OBTAIN_LICENSE("Litsenziya", "", "", "PERMITS", ""),
//    OBTAIN_PERMIT("Ruxsatnoma", "", "", "PERMITS", ""),
//    OBTAIN_CONCLUSION("Xulosa", "", "", "PERMITS", ""),

    // Xavfli ishlab chiqarish obyektlari kadastr pasporti
    REGISTER_CADASTRE_PASSPORT("TXYuZ kadastr pasportini ro‘yxatga olish", "registerCadastrePassport", "", "CADASTRE", ""),
//    DEREGISTER_CADASTRE_PASSPORT("", "", "", "CADASTRE", ""),

    // Sanoat xavfsizligi deklaratsiyasi
    REGISTER_DECLARATION("Sanoat deklaratsiyasini ro‘yxatga olish", "registerDeclaration", "", "CADASTRE", ""),
//    DEREGISTER_DECLARATION("", "", "", "CADASTRE", ""),

    //IRS (Ionizing Radiation Source ) - INM ro'yhatga olish, qabul qilib olish va berish
    REGISTER_IRS("INMni ro'yxatga olish", "registerIrs", "", "IRS", ""),
    ACCEPT_IRS("INMni olish", "irs", "", "IRS", ""),
    TRANSFER_IRS("INMni berish", "irs", "", "IRS", ""),

    // Attestation
    ATTESTATION_COMMITTEE("XIChO rahbar xodimlarini attestasiyadan o‘tkazish", "registerAttestation", "", "ATTESTATION_COMMITTEE", ""),
    ATTESTATION_REGIONAL("XIChO texnik va ishchi xodimlarini attestasiyadan o‘tkazish", "registerAttestation", "", "ATTESTATION_REGIONAL", "");

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
