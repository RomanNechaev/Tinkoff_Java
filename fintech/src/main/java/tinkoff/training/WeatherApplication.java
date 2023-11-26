package tinkoff.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import tinkoff.training.config.WeatherClientProperties;

@SpringBootApplication
@EnableConfigurationProperties({WeatherClientProperties.class})
@EnableScheduling
public class WeatherApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }
}