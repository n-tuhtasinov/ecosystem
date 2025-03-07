package uz.technocorp.ecosystem.modules.appeal.enums;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 19.02.2025
 * @since v1.0
 */
public enum AppealType {

    // Xavfli Ishlab Chiqarish Obyektini ro‘yxatga olish va chiqarish
    RegisterHPO,
    DeregisterHPO,

    // Xavfli ishlab chiqarish qurilmalarini ro‘yxatdan o‘tkazish
    RegisterCrane,
    RegisterVessel,
    RegisterBoiler,
    RegisterLift,
    RegisterEscalator,
    RegisterBridgeOrRoad,
    RegisterElevator,
    RegisterPipeline,
    RegisterAttractionPassport,
    RegisterAttraction,
    RegisterPressureVesselChemical,
    RegisterSteamAndHotWaterPipeline,
    RegisterBoilerUtilizer,
    RegisterPressureVesselLPG,
    RegisterHighGasUsageEquipment,

    // Xavfli ishlab chiqarish qurilmalarini ro‘yxatdan chiqarish
    DeregisterCrane,
    DeregisterVessel,
    DeregisterBoiler,
    DeregisterLift,
    DeregisterEscalator,
    DeregisterBridgeOrRoad,
    DeregisterElevator,
    DeregisterPipeline,
    DeregisterAttractionPassport,
    DeregisterAttraction,

    // Xavfli ishlab chiqarish obyektlari xodimlarini attestatsiyadan o‘tkazish
    CertifyHPOEmployee,

    // Ekspert tashkilotini akkreditatsiya qilish
    AccreditExpertOrganization,

    // Sanoat xavfsizligi bo‘yicha ekspertiza xulosalari
    RegisterSafetyExpertConclusion,
    DeregisterSafetyExpertConclusion,

    // Litsenziya va ruxsatnomalar
    ObtainLicense,
    ObtainPermit,
    ObtainConclusion,

    // Xavfli ishlab chiqarish obyektlari kadastr pasporti
    RegisterHPOCadastrePassport,
    DeregisterHPOCadastrePassport,

    // Sanoat xavfsizligi deklaratsiyasi
    RegisterSafetyDeclaration,
    DeregisterSafetyDeclaration,

    // INM (Individual Number for Manufacturing)
    ObtainINM,
    IssueINM,
    RegisterINM,
    DeregisterINM,

    // Boshqa yo‘nalishlar
    Other,

    // Ekspert tashkiloti akkreditatsiyasi
    ReaccreditExpertOrganization,
    ExpandAccreditationScope

    }
