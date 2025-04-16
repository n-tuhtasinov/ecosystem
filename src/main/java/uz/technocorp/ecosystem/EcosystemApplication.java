package uz.technocorp.ecosystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableFeignClients
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class EcosystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcosystemApplication.class, args);
    }

}
