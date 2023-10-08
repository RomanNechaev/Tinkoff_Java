package tinkoff.training.services.crud;

import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherModel;

public interface UpdateOperation {
    /**
     * Обновить информацию о городе
     *
     * @param city               - название города
     * @param weatherUpdateModel - обновленная информацию о городе
     * @return объект Weather
     * @see Weather
     */
    Weather update(String city, WeatherModel weatherUpdateModel);
}
