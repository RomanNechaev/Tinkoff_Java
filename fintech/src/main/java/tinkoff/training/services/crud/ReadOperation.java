package tinkoff.training.services.crud;

import tinkoff.training.entities.Weather;

public interface ReadOperation {
    /**
     * Получить данные о погоде в городе name
     *
     * @param name - название города
     * @return объект Weather
     * @see Weather
     */
    Weather getWeatherByCityName(String name);
}
