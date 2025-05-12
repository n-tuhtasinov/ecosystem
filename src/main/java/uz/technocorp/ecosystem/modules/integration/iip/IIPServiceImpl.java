package uz.technocorp.ecosystem.modules.integration.iip;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 12.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IIPServiceImpl implements IIPService {

    private final IIProperties properties;
    private final RestClient restClient;

    @Override
    public String getToken() {
        String credentials = properties.getConsumerKey()+":"+properties.getConsumerSecret();
        String base64 = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("username", properties.getUsername());
        formData.add("password", properties.getPassword());

        Map response = restClient.post()
                .uri(properties.getTokenUrl())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
//                .onStatus(HttpStatusCode::isError,
//                        (req, res)-> {throw new RuntimeException("MIP dan token olishda xatolik yuz berdi");}
//                )
                .body(Map.class);

        if (response==null){
            throw new RuntimeException("MIP dan token olishda bo'sh javob qaytdi");
        }

        if (response.containsKey("access_token")) {
            return (String) response.get("access_token");
        }

        throw new RuntimeException("MIP dan token olishda xatolik yuz berdi =>: " + response);
    }
}
