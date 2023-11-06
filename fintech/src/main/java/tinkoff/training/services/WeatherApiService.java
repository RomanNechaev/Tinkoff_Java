package tinkoff.training.services;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tinkoff.training.entities.Weather;

public interface WeatherApiService {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    Weather getWeatherByCityName(String name);
}
