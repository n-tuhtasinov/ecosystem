package uz.technocorp.ecosystem.modules.document.enums;

import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;

import java.util.Arrays;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.02.2025
 * @since v1.0
 */
public enum DocumentType {

    Report("Ma'lumotnoma"),
    Protocol("Bayonnoma"),
    Act("Dalolatnoma"),
    Order("Buyruq"),
    Decree("Qaror"),
    Notification("Xabarnoma"),
    Appeal("Ariza"),
    ReplyLetter("Javob xati"),
    AccreditationCertificate("Akkreditatsiya attestati");

    public final String displayName;

    DocumentType(String displayName) {
        this.displayName = displayName;
    }

}
