package uz.technocorp.ecosystem.modules.prevention.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 14.05.2025
 * @since v1.0
 */
@Getter
@AllArgsConstructor
public enum PreventionType {
    TYPE1(1, "Huquqiy targ'ibot"),
    TYPE2(2, "Seminar"),
    TYPE3(3, "Tushuntirish ishlari"),
    TYPE4(4, "Amaliy mashg'ulot"),
    TYPE5(5, "Qonunchilik talablari buzilishining oldini olishga qaratilgan tavsiyalar"),
    TYPE6(6, "Sodir etilayotgan tizimli huquqbuzarliklarning sabablari"),
    TYPE7(7, "Huquqbuzarliklarga imkon beruvchi shart-sharoitlar bo'yicha xabarnomalar"),
    TYPE8(8, "OAV chiqishlar (rolik, yangilikka havola"),
    TYPE9(9, "Veb-saytga ilova"),
    TYPE10(10, "Blogga ilova"),
    TYPE11(11, "Chatga ilova"),
    TYPE12(12, "\"Ochiq Eshiklar Kuni\" tadbirini tashkil etish va ularda qatnashish to'g'risida xabarnoma"),
    TYPE13(13, "Qonunchilik talablariga oid ma'lumotlar"),
    TYPE14(14, "Tarqatma materiallar"),
    TYPE15(15, "Qo'llanmalar"),
    TYPE16(16, "Tekshirish ro'yxati (Checklist)"),
    TYPE17(17, "Boshqa");

    private final Integer id;
    private final String name;

    public static PreventionType find(Integer id) {
        return Stream.of(PreventionType.values())
                .filter(a -> id != null && id.equals(a.getId()))
                .findFirst().orElse(null);
    }
}
