package uz.technocorp.ecosystem.modules.eimzo.json.pcks7;

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
public class Pkcs7SubjectNameJson {

    @JsonAlias({"1.2.860.3.16.1.2"})
    private String tin;

    @JsonAlias({"CN"})
    private String name;

}