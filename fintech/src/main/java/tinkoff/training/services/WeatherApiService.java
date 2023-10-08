package tinkoff.training.services;

import tinkoff.training.models.WeatherModel;

public interface WeatherApiService {
    WeatherModel getWeatherByCityName(String name);
}
