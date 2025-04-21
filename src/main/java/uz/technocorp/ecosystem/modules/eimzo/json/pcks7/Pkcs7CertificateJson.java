package uz.technocorp.ecosystem.modules.eimzo.json.pcks7;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 14.04.2025
 * @since v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pkcs7CertificateJson {

    private String serialNumber;
    private String subjectName;
    private String validFrom;
    private String validTo;
    private String issuerName;
    private Pkcs7PublicKeyJson publicKey;
    private Pkcs7SignatureJson signature;

}
