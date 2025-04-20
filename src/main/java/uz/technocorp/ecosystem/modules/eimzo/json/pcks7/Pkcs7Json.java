package uz.technocorp.ecosystem.modules.eimzo.json.pcks7;

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
public class Pkcs7Json {

    private String pkcs7b64;
    private List<Pkcs7TimestampedSignerJson> timestampedSignerList;
    private String status;
    private String message;

}
