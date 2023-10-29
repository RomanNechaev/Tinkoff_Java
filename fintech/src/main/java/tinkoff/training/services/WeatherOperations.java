package tinkoff.training.services;

import tinkoff.training.entities.Weather;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WeatherOperations {
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
    Map<Long, List<Double>> idToTemperature();

    /**
     * Преобразовать список в Map, у которой ключ - температура, значение - коллекция объектов Weather,
     * которым соответствует температура, указанная в ключе
     *
     * @return Map: temperatureToWeather
     * @see Weather
     */
    Map<Double, List<Weather>> toSameWeathereMap();
}
