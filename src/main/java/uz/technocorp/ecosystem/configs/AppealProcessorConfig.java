package uz.technocorp.ecosystem.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.processor.AppealPdfProcessor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class AppealProcessorConfig {

    @Bean
    @ConditionalOnProperty(name = "appeal.processors.enabled", havingValue = "true", matchIfMissing = true)
    public Map<Class<? extends AppealDto>, AppealPdfProcessor> appealProcessorMap(List<AppealPdfProcessor> processorList) {
        return processorList.stream()
                .collect(Collectors.toMap(
                        AppealPdfProcessor::getSupportedType,
                        Function.identity(),
                        (existing, replacement) -> {
                            throw new IllegalStateException(
                                    "Duplicate processor for type: " + existing.getSupportedType().getSimpleName()
                            );
                        }
                ));
    }
}