package uz.technocorp.ecosystem.modules.document.enums;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
public enum DocumentType {

    REPORT("Ma'lumotnoma"),
//    PROTOCOL("Bayonnoma"),
    ACT("Dalolatnoma"),
//    ORDER("Buyruq"),
    DECREE("Qaror"),
//    NOTIFICATION("Xabarnoma"),
    APPEAL("Ariza"),
    REPLY_LETTER("Javob xati"),
    ACCREDITATION_CERTIFICATE("Akkreditatsiya attestati");

    public final String displayName;

    DocumentType(String displayName) {
        this.displayName = displayName;
    }

}
