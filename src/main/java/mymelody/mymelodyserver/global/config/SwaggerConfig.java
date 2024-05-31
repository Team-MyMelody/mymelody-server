package mymelody.mymelodyserver.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() { // Swagger API 명세서에 들어왔을 때 어떻게 보여줄 지 Custom
        Info info = new Info()
                .version("v1.0.0")
                .title("MyMelody API Document")
                .description("MyMelody API 문서입니다.");

        //security

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
    }
}
