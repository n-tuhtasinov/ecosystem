package uz.technocorp.ecosystem.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.development-mode}")
    private String developmentMode;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // handle SPA routing
//        registry.addResourceHandler("/**")
//                .addResourceLocations("file:"+System.getProperty("user.dir")+"/static/", "")
//                .resourceChain(false)
//                .addResolver(new PushStateResourceResolver());


        // serve media files from directly a specific folder without any controller
        registry.addResourceHandler("files/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")+"/files/")
                .setCachePeriod(60*60) // 1 hour
                .resourceChain(true); // true - automatically cached by browser
    }


}
