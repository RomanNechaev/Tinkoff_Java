package tinkoff.training.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tinkoff.training.client.WeatherApiClient;
import tinkoff.training.models.WeatherModel;
import tinkoff.training.services.ExceptionWeatherApiHandler;
import tinkoff.training.services.WeatherApiService;
import tinkoff.training.utils.exceptions.ApiException;

@Service
@RequiredArgsConstructor
public class WeatherApiServiceImpl implements WeatherApiService {

    private final WeatherApiClient weatherApiClient;
    private final ExceptionWeatherApiHandler exceptionWeatherApiHandler;

    public Mono<WeatherModel> getWeatherByCityName(String name) {
        return weatherApiClient
                .getCurrentWeather(name)
                .map(response -> {
                    String[] currentDateTime = response.getCurrent().getLastUpdated().split(" ");
                    String lastTimeUpdated = currentDateTime[0];
                    String lastDateUpdated = currentDateTime[1];
                    return new WeatherModel(
                            response.getLocation().getName(),
                            response.getCurrent().getTemperatureCelsius(),
                            lastTimeUpdated,
                            lastDateUpdated);
                })
                .doOnError(e -> exceptionWeatherApiHandler.handle((ApiException) e));
    }
}
