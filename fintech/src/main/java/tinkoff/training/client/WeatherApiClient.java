package tinkoff.training.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tinkoff.training.config.WeatherClientProperties;
import tinkoff.training.models.WeatherApiError;
import tinkoff.training.models.WeatherApiResponse;
import tinkoff.training.utils.exceptions.ApiException;


@Component
@RequiredArgsConstructor
public class WeatherApiClient {

    private final WebClient webClient;
    private final WeatherClientProperties properties;

    public Mono<WeatherApiResponse> getCurrentWeather(String city) {
        return webClient.get()
                .uri(properties.getCurrentWeatherUri(), city, properties.getKey())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    int httpCode = response.statusCode().value();
                    return response.bodyToMono(WeatherApiError.class)
                            .flatMap(error -> Mono.error(new ApiException(error, httpCode)));
                })
                .bodyToMono(WeatherApiResponse.class);
    }
}
