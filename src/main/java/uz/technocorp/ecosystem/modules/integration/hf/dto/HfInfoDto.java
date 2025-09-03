package uz.technocorp.ecosystem.modules.integration.hf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.technocorp.ecosystem.shared.dto.FileDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Suxrob
 * @version 1.0
 * @created 08.08.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HfInfoDto {

    private String name;
    private LocalDate registrationDate;
    private String registryNumber;
    private String regionName;
    private String districtName;
    private String address;
    private String upperOrganization;
    private String location;
    private String hazardousSubstance;
    private String hfTypeName;
    private String extraArea;
    private String description;
    private List<String> spheres;
    private String periodicUpdateReason;
    private String periodicUpdateFilePath;
    private Boolean active;
    private Map<String, FileDto> files;
    private String registryFilePath;
}
