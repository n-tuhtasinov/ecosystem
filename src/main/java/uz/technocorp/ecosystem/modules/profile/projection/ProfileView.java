package uz.technocorp.ecosystem.modules.profile.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 13.05.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileView {

    private String id;
    private Long tin;
    private String legalName;
    private String legalAddress;
}
