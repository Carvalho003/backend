package school.sptech.EncantoPersonalizados.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import school.sptech.EncantoPersonalizados.infrastructure.config.WebConfig;

@SpringBootTest(classes = WebConfig.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Import(WebConfig.class)
class WebConfigTest {

    @Test
    @DisplayName("Config - WebConfig - contexto sobe sem DataSource/JPA")
    void contextLoads() {
        // Se o contexto Spring subir com sucesso com WebConfig registrado, o teste passa.
        // Auto-configuração de DataSource/JPA foi desabilitada para este teste.
    }
}
