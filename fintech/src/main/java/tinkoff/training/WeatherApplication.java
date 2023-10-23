package tinkoff.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tinkoff.training.config.WeatherClientProperties;

@SpringBootApplication
@EnableConfigurationProperties({WeatherClientProperties.class})
public class WeatherApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }
}