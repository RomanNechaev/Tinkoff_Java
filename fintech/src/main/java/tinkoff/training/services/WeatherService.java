package tinkoff.training.services;

import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherModel;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Функционал для объектов Weather
 *
 * @see Weather
 */
public interface WeatherService {
    /**
     * Получить среднее значение температуры в регионах
     *
     * @return среднее значение температуры в регионах
     * @see Weather
     */
    double getAverageTemperature();

    /**
     * Поиск регионов, у которых температура больше определенного значения
     *
     * @param temperature - значение определенной температуры
     * @return список отфлильтрованных регионов
     * @see Weather
     */
    List<String> findRegionsAboveTemperature(double temperature);

    /**
     * Преобразовать список в Map, у которой ключ - уникальный идентификатор, значение - список со значениями температур
     *
     * @return Map: idToTemperature
     * @see Weather
     */
    Map<UUID, List<Double>> idToTemperature();

    /**
     * Преобразовать список в Map, у которой ключ - температура, значение - коллекция объектов Weather,
     * которым соответствует температура, указанная в ключе
     *
     * @return Map: temperatureToWeather
     * @see Weather
     */
    Map<Double, List<Weather>> toSameWeathereMap();

    /**
     * Добавить информацию о городе
     *
     * @param city               - название города
     * @param weatherCreateModel - регистрационная модель города
     * @return объект Weather
     * @see Weather
     */
    Weather create(String city, WeatherModel weatherCreateModel);

    /**
     * Обновить информацию о городе
     *
     * @param city               - название города
     * @param weatherUpdateModel - обновленная информацию о городе
     * @return объект Weather
     * @see Weather
     */
    Weather update(String city, WeatherModel weatherUpdateModel);

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

}
