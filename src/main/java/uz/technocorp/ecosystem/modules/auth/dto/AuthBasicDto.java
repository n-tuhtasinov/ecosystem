package uz.technocorp.ecosystem.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Suxrob
 * @version 1.0
 * @created 21.07.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthBasicDto {

    private String username;
    private String password;
}
