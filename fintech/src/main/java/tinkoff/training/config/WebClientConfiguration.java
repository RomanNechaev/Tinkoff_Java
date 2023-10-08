package tinkoff.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Configuration
@PropertySource("classpath:client.properties")
public class WebClientConfiguration {
    final Environment environment;

    public WebClientConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "default")
    public WebClient webCLient() {
        return WebClient.builder()
                .baseUrl(Objects.requireNonNull(environment.getProperty("api.base_url")))
                .build();
    }
}
