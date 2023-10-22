package tinkoff.training.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;
import tinkoff.training.client.WeatherApiClient;
import tinkoff.training.entities.City;
import tinkoff.training.entities.WeatherEntity;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.services.ExceptionWeatherApiHandler;
import tinkoff.training.services.WeatherApiService;
import tinkoff.training.services.spring_data_jpa.CrudService;
import tinkoff.training.utils.exceptions.ApiException;

@Service
@RequiredArgsConstructor
public class WeatherApiServiceImpl implements WeatherApiService {

    private final WeatherApiClient weatherApiClient;
    private final ExceptionWeatherApiHandler exceptionWeatherApiHandler;
    private final CrudService<WeatherEntity> weatherEntityCrudService;
    private final CrudService<City> cityService;
    private final CrudService<WeatherType> weatherTypeCrudService;

    public WeatherEntity getWeatherByCityName(String name) {
        return weatherApiClient
                .getCurrentWeather(name)
                .publishOn(Schedulers.boundedElastic())
                .map(response -> {
                    String[] currentDateTime = response.getCurrent().getLastUpdated().split(" ");
                    String cityName = response.getLocation().getName();
                    String lastDateUpdated = currentDateTime[0];
                    String lastTimeUpdated = currentDateTime[1];
                    Double temperature = response.getCurrent().getTemperatureCelsius();
                    String weatherType = response.getCurrent().getCondition().getWeatherType();
                    WeatherType weatherType1 = weatherTypeCrudService.findByName(weatherType);
                    City city = cityService.findByName(cityName);
                    WeatherEntity weatherEntity = new WeatherEntity(temperature, lastDateUpdated, lastTimeUpdated, city, weatherType1);
                    weatherEntityCrudService.create(weatherEntity);
                    return weatherEntity;
                })
                .doOnError(e -> exceptionWeatherApiHandler.handle((ApiException) e))
                .block();
    }
}
