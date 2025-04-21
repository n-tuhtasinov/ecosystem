package uz.technocorp.ecosystem.modules.eimzo.json.eimzo;

import com.fasterxml.jackson.annotation.JsonAlias;
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
public class EImzoVpnKeyInfoJson {

    private String serialNumber;

    @JsonAlias({"X500Name"})
    private String X500Name;

    private String validFrom;
    private String validTo;

}
