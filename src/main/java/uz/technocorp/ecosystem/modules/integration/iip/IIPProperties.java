package uz.technocorp.ecosystem.modules.integration.iip;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 12.05.2025
 * @since v1.0
 */
@Component
@ConfigurationProperties(prefix = "app.iip")
@Data
public class IIPProperties {
    private String tokenUrl;
    private String username;
    private String password;
    private String consumerKey;
    private String consumerSecret;

}
