package tinkoff.training.services;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tinkoff.training.entities.WeatherEntity;

public interface WeatherApiService {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    WeatherEntity getWeatherByCityName(String name);
}
