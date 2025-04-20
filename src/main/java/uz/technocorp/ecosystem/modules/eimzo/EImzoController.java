package uz.technocorp.ecosystem.modules.eimzo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.models.AppConstants;
import uz.technocorp.ecosystem.modules.eimzo.dto.SignDto;
import uz.technocorp.ecosystem.modules.eimzo.dto.StatusDto;
import uz.technocorp.ecosystem.modules.eimzo.helper.Helper;
import uz.technocorp.ecosystem.modules.eimzo.json.eimzo.EImzoAuthJson;
import uz.technocorp.ecosystem.modules.eimzo.json.eimzo.EImzoChallengeJson;
import uz.technocorp.ecosystem.modules.eimzo.json.eimzo.EImzoPingJson;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7InfoJson;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7Json;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7VerifyAttachedJson;
import uz.technocorp.ecosystem.modules.eimzo.json.pcks7.Pkcs7VerifyDetachedJson;

import java.time.LocalDateTime;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 14.04.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/api/v1/e-imzo")
@RequiredArgsConstructor
public class EImzoController {

    private final EImzoProxy eImzoProxy;

    @GetMapping("/status")
    public ResponseEntity<StatusDto> getStatus() {
        try {
            EImzoPingJson ping = eImzoProxy.ping();
            return ResponseEntity.ok(new StatusDto("up", ping.getServerDateTime(), ping.getYourIP()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.ok(new StatusDto("down", LocalDateTime.now().toString()));
    }

    @GetMapping("/challenge")
    public ResponseEntity<EImzoChallengeJson> getChallenge() {
        try {
            return ResponseEntity.ok(eImzoProxy.challenge());
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<EImzoAuthJson> auth(@Valid @RequestBody SignDto form, Authentication authentication, HttpServletRequest request) {
        try {
            EImzoAuthJson eImzoAuth = eImzoProxy.auth(AppConstants.HOST, Helper.getIp(request), form.getSign());
            return ResponseEntity.ok(eImzoAuth);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @PostMapping("/attach-timestamp")
    public ResponseEntity<Pkcs7Json> attachTimestamp(@Valid @RequestBody SignDto form, Authentication authentication, HttpServletRequest request) {
        try {
            Pkcs7Json pkcs7 = eImzoProxy.attachTimestamp(AppConstants.HOST, Helper.getIp(request), form.getSign());
            return ResponseEntity.ok(pkcs7);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @PostMapping("/attached")
    public ResponseEntity<Pkcs7VerifyAttachedJson> attached(@Valid @RequestBody SignDto form, Authentication authentication, HttpServletRequest request) {
        try {
            Pkcs7VerifyAttachedJson pkcs7VerifyAttached = eImzoProxy.pkcs7Attached(AppConstants.HOST, Helper.getIp(request), form.getSign());
            if ("1".equals(pkcs7VerifyAttached.getStatus())) {
                Pkcs7InfoJson pkcs7Info = pkcs7VerifyAttached.getPkcs7Info();
                if (pkcs7Info != null) {
                    pkcs7Info.setDocument(new String(Helper.decodeFile(pkcs7Info.getDocumentBase64())));
                    return ResponseEntity.ok(pkcs7VerifyAttached);
                } else {
                    throw new ResourceNotFoundException("Document verification failed");
                }
            } else {
                throw new ResourceNotFoundException(pkcs7VerifyAttached.getMessage());
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @PostMapping("/detached")
    public ResponseEntity<Pkcs7VerifyDetachedJson> detached(@RequestBody SignDto form, Authentication authentication, HttpServletRequest request) {
        try {
            Pkcs7VerifyDetachedJson pkcs7VerifyDetached = eImzoProxy.pkcs7Detached(AppConstants.HOST, Helper.getIp(request), form.getSign());
            return ResponseEntity.ok(pkcs7VerifyDetached);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @PostMapping("/make-attached")
    public ResponseEntity<Pkcs7VerifyAttachedJson> makeAttached(@RequestBody SignDto form, Authentication authentication, HttpServletRequest request) {
        try {
            Pkcs7VerifyAttachedJson pkcs7VerifyAttached = eImzoProxy.pkcs7Attached(AppConstants.HOST, Helper.getIp(request), form.getSign());
            return ResponseEntity.ok(pkcs7VerifyAttached);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @PostMapping("/join")
    public ResponseEntity<Pkcs7Json> join(@RequestBody SignDto form, Authentication authentication, HttpServletRequest request) {
        try {
            Pkcs7Json pkcs7 = eImzoProxy.pkcs7Join(AppConstants.HOST, Helper.getIp(request), form.getSign());
            return ResponseEntity.ok(pkcs7);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }
}