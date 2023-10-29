package tinkoff.training.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.mappers.WeatherMapper;
import tinkoff.training.models.WeatherDto;
import tinkoff.training.services.spring_data_jpa.CrudService;

@Component
public class WeatherMapperImpl implements WeatherMapper {
    private final CrudService<City> cityCrudService;
    private final CrudService<WeatherType> weatherTypeCrudService;
    @Autowired
    public WeatherMapperImpl(CrudService<City> cityCrudService, CrudService<WeatherType> weatherTypeCrudService){
        this.cityCrudService = cityCrudService;
        this.weatherTypeCrudService = weatherTypeCrudService;
    }
    @Override
    public WeatherDto toDTO(Weather weather) {
        if (weather==null) return null;
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId(weather.getId());
        weatherDto.setCity(weather.getCity().getName());
        weatherDto.setType(weather.getType().getType());
        weatherDto.setTime(weather.getTime());
        weatherDto.setDate(weather.getDate());
        weatherDto.setTemperature(weather.getTemperature());
        return weatherDto;

    }

    @Override
    public Weather toWeather(WeatherDto weatherDto) {
        if(weatherDto==null) return null;
        Weather weather = new Weather();
        City city = cityCrudService.findByName(weatherDto.getCity());
        WeatherType weatherType = weatherTypeCrudService.findByName(weatherDto.getType());
        weather.setId(weather.getId());
        weather.setCity(city);
        weather.setTime(weatherDto.getTime());
        weather.setTemperature(weatherDto.getTemperature());
        weather.setDate(weatherDto.getDate());
        weather.setType(weatherType);
        return weather;
    }
}
