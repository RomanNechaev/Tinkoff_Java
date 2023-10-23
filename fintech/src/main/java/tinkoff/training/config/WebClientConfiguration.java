package tinkoff.training.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Configuration
public class WebClientConfiguration {
    private final WeatherClientProperties weatherClientProperties;

    @Autowired
    public WebClientConfiguration(WeatherClientProperties weatherClientProperties) {
        this.weatherClientProperties = weatherClientProperties;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(Objects.requireNonNull(weatherClientProperties.getBaseUrl()))
                .build();
    }
}
