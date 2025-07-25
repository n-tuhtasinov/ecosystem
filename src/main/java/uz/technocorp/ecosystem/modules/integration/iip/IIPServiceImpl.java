package uz.technocorp.ecosystem.modules.integration.iip;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.office.Office;
import uz.technocorp.ecosystem.modules.office.OfficeService;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.user.dto.IndividualUserDto;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
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

    private final IIPProperties properties;
    private final RestClient restClient;
    private final DistrictService districtService;
    private final RegionService regionService;
    private final OfficeService officeService;


    private String getToken() {
        String credentials = properties.getConsumerKey() + ":" + properties.getConsumerSecret();
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

        if (response == null) {
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

        if (node == null) throw new RuntimeException("MIP dan STIR bo'yicha so'rovga bo'sh javob qaytdi");

        //make legalUserDto
        Integer districtSoato = node.get("companyBillingAddress").get("soato").asInt();

        //except two district soato (1714401365 - Namangan shahar, Davlatobod tumani, 1714401367- Namangan shahar, Yangi Namangan tumani)
        if (districtSoato.toString().length() > 7 && !districtSoato.equals(1714401365) && !districtSoato.equals(1714401367)) {
            String substring = districtSoato.toString().substring(0, 7);
            districtSoato = Integer.parseInt(substring);
        }

        District district = districtService.findBySoato(districtSoato);
        Region region = regionService.findById(district.getRegionId());
        Office office = officeService.findByRegionId(region.getId());

        return new LegalUserDto(
                Long.valueOf(tin),
                (!node.get("company").get("shortName").isNull() && !node.get("company").get("shortName").textValue().isBlank())
                        ? node.get("company").get("shortName").textValue()
                        : node.get("company").get("name").textValue(),
                node.get("companyBillingAddress").get("streetName").textValue(),
                node.get("director").get("lastName").textValue() + " " + node.get("director").get("firstName").textValue() + " " + node.get("director").get("middleName").textValue(),
                district.getRegionId(),
                district.getId(),
                node.get("directorContact").isNull() ? null : node.get("directorContact").get("phone").textValue(),
                node.get("company").get("kfs").asText(),
                node.get("company").get("opf").asText(),
                office.getId());

    }

    @Override
    public IndividualUserDto getPinInfo(String pin, LocalDate birthDate) {

        //get token from IIP
        String token = getToken();

        //get gnkInfo as jsonNode
        Map<String, Object> fields = new HashMap<>();
        fields.put("transaction_id", 3);
        fields.put("is_consent", "Y");
        fields.put("sender_pinfl", pin);
        fields.put("langId", 3);
        fields.put("birth_date", birthDate.toString());
        fields.put("pinpp", pin);
        fields.put("is_photo", "N");
        fields.put("Sender", "P");

        JsonNode node = restClient.post()
                .uri(properties.getIndividualUrl())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fields)
                .retrieve()
                .body(JsonNode.class);

        if (node == null || node.get("data").isNull())
            throw new RuntimeException("MIP dan JSHSHIR bo'yicha so'rovga bo'sh javob qaytdi");

        //make individual user dto
        JsonNode data = node.withArray("data").get(0);
        String fullName = data.get("surnamelat").asText() + " " + data.get("namelat").asText() + " " + data.get("patronymlat").asText("");
        return new IndividualUserDto(fullName, Long.valueOf(pin), null);
    }
}
