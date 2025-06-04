package uz.technocorp.ecosystem.modules.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 04.06.2025
 * @since v1.0
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    @PostMapping
    public ResponseEntity<?> create(){
        testRepository.save(new TestEntity("hhhh", Map.of("param1", 1, "param2", "qandaydir value")));
        return ResponseEntity.ok().build();
    }
}
