package uz.technocorp.ecosystem.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
public class ApiIntegrator<T> {

    public T getData(Class<T> tClass, Map<String, String> params, String url){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            map.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, request, tClass);
        if (responseEntity.getStatusCode().value()==200 && responseEntity.getBody()!=null ){
            return responseEntity.getBody();
        }
        return null;
    }

}
