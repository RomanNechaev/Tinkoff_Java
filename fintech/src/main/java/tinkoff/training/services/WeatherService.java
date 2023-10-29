package tinkoff.training.services;

import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherDto;
import tinkoff.training.models.WeatherModel;

/**
 * Функционал для объектов Weather
 *
 * @see Weather
 */
public interface WeatherService extends WeatherOperations {
    /**
     * Добавить информацию о городе
     *
     * @param city               - название города
     * @param weatherDto - регистрационная модель города
     * @return объект Weather
     * @see Weather
     */
    Weather create(String city, WeatherDto weatherDto);

    /**
     * Удалить город
     *
     * @param city -  имя города
     */
    void delete(String city);

    /**
     * Получить данные о погоде в городе name
     *
     * @param name - название города
     * @return объект Weather
     * @see Weather
     */
    Weather getWeatherByCityName(String name);

    /**
     * Обновить информацию о городе
     *
     * @param city               - название города
     * @param weatherDto - обновленная информацию о городе
     * @return объект Weather
     * @see Weather
     */
    Weather update(String city, WeatherDto weatherDto);
}
