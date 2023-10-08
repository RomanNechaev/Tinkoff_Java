package tinkoff.training.services;

import tinkoff.training.entities.Weather;
import tinkoff.training.services.crud.*;

/**
 * Функционал для объектов Weather
 *
 * @see Weather
 */
public interface WeatherService extends WeatherOperations, CrudOperation {
}
