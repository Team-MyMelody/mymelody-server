package mymelody.mymelodyserver.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        ArrayList<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("*");
        allowedOriginPatterns.add("http://localhost:3000");
        allowedOriginPatterns.add("http://localhost:8080");
        allowedOriginPatterns.add("https://mymelody.shop");
        allowedOriginPatterns.add("https://mymelody.shop/");
        allowedOriginPatterns.add("https://mymelody-web.vercel.app");
        allowedOriginPatterns.add("https://mymelody-web.vercel.app/");
        allowedOriginPatterns.add("https://mymelody-web.vercel.app/callback");

        //추가
        String[] patterns = allowedOriginPatterns.toArray(String[]::new);
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOriginPatterns(patterns)
                .allowCredentials(true)
                .maxAge(3600L);
    }
}
