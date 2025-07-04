package uz.technocorp.ecosystem.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.technocorp.ecosystem.modules.auth.enums.AuthMethod;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoFromOneIdDto {

    private String full_name;
    private String birth_date;
    private String mob_phone_no;
    private String pin;
    private String user_type;
    private AuthMethod auth_method;
    private String pkcs_legal_tin;
    private List<String> legal_info;

}
