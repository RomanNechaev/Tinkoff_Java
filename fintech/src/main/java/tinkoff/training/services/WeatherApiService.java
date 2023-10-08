package tinkoff.training.services;

import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherModel;
import tinkoff.training.services.crud.ReadOperation;

public interface WeatherApiService {
    WeatherModel getWeatherByCityName(String name);
}
