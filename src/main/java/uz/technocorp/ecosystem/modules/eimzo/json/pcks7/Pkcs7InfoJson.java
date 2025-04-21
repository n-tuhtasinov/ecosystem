package uz.technocorp.ecosystem.modules.eimzo.json.pcks7;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Pkcs7InfoJson {

    private List<Pkcs7SignerJson> signers;
    private String documentBase64;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String document;

}