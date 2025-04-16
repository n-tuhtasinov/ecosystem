package uz.technocorp.ecosystem.modules.eimzo.json.eimzo;

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
public class EImzoPingJson {

    private String serverDateTime;
    private String yourIP;
    private EImzoVpnKeyInfoJson vpnKeyInfo;

}
