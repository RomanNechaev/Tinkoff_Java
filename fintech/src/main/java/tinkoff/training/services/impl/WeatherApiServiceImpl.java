package tinkoff.training.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tinkoff.training.client.WeatherApiClient;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherModel;
import tinkoff.training.services.ExceptionWeatherApiHandler;
import tinkoff.training.services.WeatherApiService;
import tinkoff.training.utils.exceptions.ApiException;

@Service
public class WeatherApiServiceImpl implements WeatherApiService {

    private final WeatherApiClient weatherApiClient;
    private final ExceptionWeatherApiHandler exceptionWeatherApiHandler;

    @Autowired
    public WeatherApiServiceImpl(WeatherApiClient weatherApiClient, ExceptionWeatherApiHandler exceptionWeatherApiHandler) {
        this.weatherApiClient = weatherApiClient;
        this.exceptionWeatherApiHandler = exceptionWeatherApiHandler;
    }

    @Override
    public Weather getWeatherByCityName(String name) {
        Mono<WeatherModel> response = weatherApiClient.getCurrentWeather(name).doOnError(x -> exceptionWeatherApiHandler.handle((ApiException) x));

    }
}
