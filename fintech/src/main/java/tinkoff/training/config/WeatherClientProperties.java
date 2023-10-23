package tinkoff.training.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class WeatherClientProperties {
    private String key;
    private String baseUrl;
    private String currentWeatherUri;
}
