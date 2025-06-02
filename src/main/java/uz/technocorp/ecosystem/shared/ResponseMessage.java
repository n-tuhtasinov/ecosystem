package uz.technocorp.ecosystem.shared;

/**
 * @author Rasulov Komil
 * @since 1.0
 * @version 1.0
 * Custom message for response's body
 */
public interface ResponseMessage {

    String CREATED="Muvaffaqiyatli saqlandi";
    String UPDATED="Muvaffaqiyatli o'zgartirildi";
    String COMPLETED="Muvaffaqiyatli amalga oshirildi";
    String DELETED="Muvaffaqiyatli o'chirildi";
    String CONFLICT="Xatolik yuz berdi";
    String REJECTED= "Muvaffaqiyatli qaytarildi";

    String INVALID_TOKEN="Token yaroqsiz";
    String INCOMPLETE="Ma'lumotlar to'liq kiritilmagan";
    String CANCELLED="Amaliyot bekor qilindi";
    String LOGIN_EXIST="Bunday login sistemada mavjud, iltimos boshqa login tanlang!";
    String VALUE_EXIST="Bunday qiymat sistemada mavjud, iltimos boshqa qiymat kiriting!";
    String NOT_MATCH="Parol va parol tasdig'i mos kelmadi";
    String NOT_MATCH_OLD="Amaldagi parol noto'g'ri kiritildi";
    String FORBIDDEN="Bu amaliyot uchun sizga ruxsat berilmagan";
    String NO_SUCH_NAMED_VALUE = "Bunday nomli qiymat mavjud emas!";
    String NOT_ALLOWED_DELETE="Ushbu manba boshqa manbaga bog'langan, Avval ularni o'chirish lozim.";
    String FILE_NOT_CREATED="Faylni saqlash jarayonida xatolik yuz berdi!";
    String DIRECTORY_NOT_CREATED="Papkani saqlash jarayonida xatolik yuz berdi!";
    String ERROR_DATA="Ma'lumotlar nomlanishida yoki to'liqligida xatolik mavjud!";
}
