package uz.technocorp.ecosystem.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.technocorp.ecosystem.modules.appeal.dto.AppealDto;
import uz.technocorp.ecosystem.modules.appeal.processor.AppealPdfProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AppealProcessorConfig {

    @Bean
    public Map<Class<? extends AppealDto>, AppealPdfProcessor> appealProcessorMap(List<AppealPdfProcessor> processorList) {
        Map<Class<? extends AppealDto>, AppealPdfProcessor> processors = new HashMap<>();

        for (AppealPdfProcessor processor : processorList) {
            Class<? extends AppealDto> supportedType = processor.getSupportedType();
            processors.put(supportedType, processor);
        }
        return processors;
    }
}