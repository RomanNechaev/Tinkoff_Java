package tinkoff.training.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
@Getter
public class WeatherClientProperties {
    private String key;
    private String baseUrl;
    private String currentWeatherUri;
}
