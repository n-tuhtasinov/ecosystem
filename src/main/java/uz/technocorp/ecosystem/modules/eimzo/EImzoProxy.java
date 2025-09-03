package uz.technocorp.ecosystem.modules.eimzo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.modules.eimzo.dto.SignedDocumentDto;
import uz.technocorp.ecosystem.modules.eimzo.json.eimzo.EImzoAuthJson;
import uz.technocorp.ecosystem.modules.eimzo.json.eimzo.EImzoChallengeJson;
import uz.technocorp.ecosystem.modules.eimzo.json.eimzo.EImzoPingJson;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7Json;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7VerifyAttachedJson;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7VerifyDetachedJson;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 14.04.2025
 * @since v1.0
 */
@FeignClient(name = "e-imzo-server", url = "${app.e-imzo.url}")
public interface EImzoProxy {

    @GetMapping("/ping")
    EImzoPingJson ping();

    @GetMapping("/frontend/challenge")
    EImzoChallengeJson challenge();

    @PostMapping("/backend/auth")
    EImzoAuthJson auth(@RequestHeader(value = "Host") String host,
                       @RequestHeader(value = "X-Real-IP") String ip,
                       @RequestBody String pkcs7);

    @PostMapping("/frontend/timestamp/pkcs7")
    Pkcs7Json attachTimestamp(@RequestHeader(value = "Host") String host,
                              @RequestHeader(value = "X-Real-IP") String ip,
                              @RequestBody String pkcs7Base64);

    @PostMapping("/backend/pkcs7/verify/attached")
    Pkcs7VerifyAttachedJson pkcs7Attached(@RequestHeader(value = "Host") String host,
                                          @RequestHeader(value = "X-Real-IP") String ip,
                                          @RequestBody String documentBase64);

    @PostMapping("/backend/pkcs7/verify/detached")
    Pkcs7VerifyDetachedJson pkcs7Detached(@RequestHeader(value = "Host") String host,
                                          @RequestHeader(value = "X-Real-IP") String ip,
                                          @RequestBody String documentBase64AndPkcs7Base64);

    @PostMapping("/frontend/pkcs7/make-attached")
    Pkcs7VerifyAttachedJson pkcs7MakeAttached(@RequestHeader(value = "Host") String host,
                                              @RequestHeader(value = "X-Real-IP") String ip,
                                              @RequestBody String documentBase64AndPkcs7Base64);

    @PostMapping("/frontend/pkcs7/join")
    Pkcs7Json pkcs7Join(@RequestHeader(value = "Host") String host,
                        @RequestHeader(value = "X-Real-IP") String ip,
                        @RequestBody String documentBase64AndPkcs7Base64);

    // Mobile
    @PostMapping("/frontend/mobile/auth")
    Object getAuthChallenge();

    @PostMapping(path = "/frontend/mobile/status", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Object getDocumentStatus(@RequestParam("documentId") String documentId);

    @GetMapping("/backend/mobile/authenticate/{docId}")
    Object authenticateWithHeaders(@PathVariable("docId") String docId,
                                   @RequestHeader("X-Real-IP") String realIp,
                                   @RequestHeader("Host") String host);

    @PostMapping("/frontend/mobile/sign")
    Object signDocument();

    @PostMapping(path = "/backend/mobile/verify", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Object verifyDocument(@RequestBody SignedDocumentDto dto,
                          @RequestHeader("X-Real-IP") String realIp,
                          @RequestHeader("Host") String host);
}