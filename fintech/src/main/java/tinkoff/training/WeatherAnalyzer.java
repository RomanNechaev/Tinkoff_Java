package tinkoff.training;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Анализ объектов Weather
 * @see tinkoff.training.Weather
 */
public class WeatherAnalyzer {
    /**
     * Получить среднее значение температуры в регионах
     *
     * @param weatherList - список объектов Weather
     * @return среднее значение температуры в регионах
     * @see tinkoff.training.Weather
     */
    public double getAverageTemperature(List<Weather> weatherList) {
        return weatherList.stream().map(Weather::getTemperatureValue).collect(Collectors.averagingDouble(x -> x));
    }

    /**
     * Поиск регионов, у которых температура больше определенного значения
     *
     * @param weatherList список объектов Weather
     * @param temperature - значение определенной температуры
     * @return список отфлильтрованных регионов
     * @see tinkoff.training.Weather
     */
    public List<String> findRegionsAboveTemperature(List<Weather> weatherList, double temperature) {
        return weatherList.stream().filter(x -> x.getTemperatureValue() > temperature).map(Weather::getName).toList();
    }

    /**
     * Преобразовать список в Map, у которой ключ - уникальный идентификатор, значение - список со значениями температур
     *
     * @param weatherList - список объектов Weather
     * @return Map: idToTemperature
     * @see tinkoff.training.Weather
     */
    public Map<UUID, List<Double>> idToTemperature(List<Weather> weatherList) {
        return weatherList.stream().collect(Collectors.groupingBy(Weather::getId, Collectors.mapping(Weather::getTemperatureValue, Collectors.toList())));
    }

    /**
     * Преобразовать список в Map, у которой ключ - температура, значение - коллекция объектов Weather,
     * которым соответствует температура, указанная в ключе
     *
     * @param weatherList - список объектов Weather
     * @return Map: temperatureToWeather
     * @see tinkoff.training.Weather
     */
    public Map<Double, List<Weather>> toSameWeathereMap(List<Weather> weatherList) {
        return weatherList.stream().collect(Collectors.groupingBy(Weather::getTemperatureValue, Collectors.toList()));
    }

}
