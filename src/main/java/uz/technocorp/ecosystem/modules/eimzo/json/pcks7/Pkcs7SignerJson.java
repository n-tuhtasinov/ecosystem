package uz.technocorp.ecosystem.modules.eimzo.json.pcks7;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class Pkcs7SignerJson {

    private Pkcs7SignerIdJson signerId;
    private String signingTime;
    private String signature;
    private String digest;
    private Pkcs7TimeStampInfoJson timeStampInfo;
    private List<Pkcs7CertificateJson> certificate;

    @JsonProperty("OCSPResponse")
    private String OCSPResponse;

    private String statusUpdatedAt;
    private String statusNextUpdateAt;
    private boolean verified;
    private boolean certificateVerified;
    private Pkcs7CertificateJson trustedCertificate;
    private List<String> policyIdentifiers;
    private boolean certificateValidAtSigningTime;

}