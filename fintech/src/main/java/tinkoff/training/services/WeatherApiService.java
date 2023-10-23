package tinkoff.training.services;

import reactor.core.publisher.Mono;
import tinkoff.training.models.WeatherModel;

public interface WeatherApiService {
    Mono<WeatherModel> getWeatherByCityName(String name);
}
