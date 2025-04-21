package uz.technocorp.ecosystem.modules.eimzo.json.pcks7;

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
public class Pkcs7VerifyAttachedJson {

    private Pkcs7InfoJson pkcs7Info;
    private String status;
    private String message;

}