package uz.technocorp.ecosystem.modules.hf.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 21.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HfCustom {

    private UUID id;
    private String name;
    private String registryNumber;
    private String regionName;
    private String districtName;
    private String address;
    private String typeName;
    private String email;
    private String legalName;
    private Long legalTin;
    private LocalDate registrationDate;
}
