package mymelody.mymelodyserver.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swaggy.swagger.customlib.config.SwaggyConfig;
import io.swaggy.swagger.customlib.utils.OpenApiChangeTracker;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SwaggyConfig.class)
public class SwaggerConfig {
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return new OpenApiChangeTracker();
    }

    @Bean
    public OpenAPI openAPI() { // Swagger API 명세서에 들어왔을 때 어떻게 보여줄 지 Custom
        Info info = new Info()
                .version("v1.0.0")
                .title("MyMelody API Document")
                .description("MyMelody API 문서입니다.");

        //security
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt); // 헤더에 토큰 포함
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
