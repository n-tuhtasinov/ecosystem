package uz.technocorp.ecosystem.modules.eimzo.json.eimzo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7SubjectCertificateInfoJson;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 14.04.2025
 * @since v1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EImzoAuthJson {

    private Pkcs7SubjectCertificateInfoJson subjectCertificateInfoJson;
    private String status;
    private String message;

}
