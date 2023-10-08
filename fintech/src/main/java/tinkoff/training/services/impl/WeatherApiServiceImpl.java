package tinkoff.training.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tinkoff.training.client.WeatherApiClient;
import tinkoff.training.models.WeatherModel;
import tinkoff.training.services.ExceptionWeatherApiHandler;
import tinkoff.training.services.WeatherApiService;
import tinkoff.training.utils.exceptions.ApiException;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class WeatherApiServiceImpl implements WeatherApiService {

    private final WeatherApiClient weatherApiClient;
    private final ExceptionWeatherApiHandler exceptionWeatherApiHandler;

    @Autowired
    public WeatherApiServiceImpl(WeatherApiClient weatherApiClient, ExceptionWeatherApiHandler exceptionWeatherApiHandler) {
        this.weatherApiClient = weatherApiClient;
        this.exceptionWeatherApiHandler = exceptionWeatherApiHandler;
    }

    public WeatherModel getWeatherByCityName(String name) {
        return weatherApiClient
                .getCurrentWeather(name)
                .doOnError(x -> exceptionWeatherApiHandler.handle((ApiException) x))
                .map(response -> {
                    String lastTimeUpdated = LocalTime.parse(response.getCurrent().getLastUpdated()).toString();
                    String lastDateUpdated = LocalDate.parse(response.getCurrent().getLastUpdated()).toString();
                    return new WeatherModel(
                            response.getLocation().getName(),
                            response.getCurrent().getTemperatureCelsius(),
                            lastTimeUpdated,
                            lastDateUpdated);
                }).block();
    }
}
