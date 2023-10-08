package tinkoff.training.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tinkoff.training.config.WeatherClientProperties;
import tinkoff.training.models.WeatherApiError;
import tinkoff.training.models.WeatherApiResponse;
import tinkoff.training.models.WeatherModel;
import tinkoff.training.utils.exceptions.ApiException;


@Component
public class WeatherApiClient {
    private final WebClient webClient;
    private final WeatherClientProperties properties;


    @Autowired
    public WeatherApiClient(@Qualifier(value = "default") WebClient webClient, WeatherClientProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public Mono<WeatherApiResponse> getCurrentWeather(String city) {
        return webClient.get()
                .uri(properties.getCurrentWeatherUri())
                .attribute("key", properties.getKey())
                .attribute("q",city)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    int httpCode = response.statusCode().value();
                    return response.bodyToMono(WeatherApiError.class)
                            .flatMap(error -> Mono.error(new ApiException(error, httpCode)));
                })
                .bodyToMono(WeatherApiResponse.class);
    }
}
