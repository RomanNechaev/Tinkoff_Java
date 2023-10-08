package tinkoff.training.services.crud;

import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherModel;

public interface CreateOperation {
    /**
     * Добавить информацию о городе
     *
     * @param city               - название города
     * @param weatherCreateModel - регистрационная модель города
     * @return объект Weather
     * @see Weather
     */
    Weather create(String city, WeatherModel weatherCreateModel);
}
