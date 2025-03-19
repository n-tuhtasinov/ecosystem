package uz.technocorp.ecosystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EcosystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcosystemApplication.class, args);
	}

}
