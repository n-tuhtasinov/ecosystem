package uz.technocorp.ecosystem.modules.integration.iip;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictRepository;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionRepository;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;

import java.nio.charset.StandardCharsets;
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
public class IIPServiceImpl implements IIPService {

    private final IIPProperties properties;
    private final RestClient restClient;
    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;

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
                .body(Map.class);

        if (response==null){
            throw new RuntimeException("MIP dan token olishda bo'sh javob qaytdi");
        }

        if (response.containsKey("access_token")) {
            return (String) response.get("access_token");
        }

        throw new RuntimeException("MIP dan token olishda xatolik yuz berdi =>: " + response);
    }

    @Override
    public LegalUserDto getGnkInfo(String tin) {

        //get token from IIP
        String token = getToken();

        //get gnkInfo as jsonNode
        JsonNode node = restClient.post()
                .uri(properties.getLegalUrl())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("tin", tin))
                .retrieve()
                .body(JsonNode.class);

        if (node==null) throw new RuntimeException("MIP dan STIR bo'yicha so'rovga bo'sh javob qaytdi");

        //make legalUserDto
        District district = districtRepository.findBySoato(node.get("companyBillingAddress").get("soato").asInt()).orElseThrow(() -> new ResourceNotFoundException("Tuman", "SOATO", node.get("companyBillingAddress").get("soato").asInt()));
        Region region = regionRepository.findById(district.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("Viloyat", "ID", district.getRegionId()));
        if (region.getOfficeId()==null) throw new RuntimeException("Ushbu tashkilot joylashgan viloyat uchun hududiy bo'lim biriktirilmagan. Viloyat: " + region.getName());
        LegalUserDto dto = new LegalUserDto(
                Long.valueOf(tin),
                (node.get("company").get("shortName").asText()!=null && !node.get("company").get("shortName").asText().trim().isEmpty()) ? node.get("company").get("shortName").asText() : node.get("company").get("name").asText(),
                node.get("companyBillingAddress").get("streetName").asText(),
                node.get("director").get("lastName").asText()+ " " +node.get("director").get("firstName").asText()+ " "+ node.get("director").get("middleName").asText(),
                district.getRegionId(),
                district.getId(),
                node.get("directorContact").get("phone").asText(),
                node.get("company").get("kfs").asText(),
                node.get("company").get("opf").asText(),
                region.getOfficeId());

        return dto;
    }
}
