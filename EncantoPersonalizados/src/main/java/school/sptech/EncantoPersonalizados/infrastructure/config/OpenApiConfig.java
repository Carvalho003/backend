package school.sptech.EncantoPersonalizados.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Projeto Encanto Personalizados",
                description = "API que servirá o front end do projeto encanto personalizados",
                contact = @Contact(
                        name = "Cynapese",
                        url = "https://github.com/",
                        email = "cynapse@email.com"
                ),
                license = @License(name = "MIT"),
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = "Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

}
