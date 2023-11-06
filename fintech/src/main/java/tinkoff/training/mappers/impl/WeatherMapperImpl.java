package tinkoff.training.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.mappers.CityMapper;
import tinkoff.training.mappers.WeatherMapper;
import tinkoff.training.mappers.WeatherTypeMapper;
import tinkoff.training.models.WeatherDto;

@Component
public class WeatherMapperImpl implements WeatherMapper {
    private final CityMapper cityMapper;
    private final WeatherTypeMapper weatherTypeMapper;

    @Autowired
    public WeatherMapperImpl(CityMapper cityMapper, WeatherTypeMapper weatherTypeMapper) {
        this.cityMapper = cityMapper;
        this.weatherTypeMapper = weatherTypeMapper;

    }

    @Override
    public WeatherDto toDTO(Weather weather) {
        if (weather == null) return null;
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId(weather.getId());
        weatherDto.setCity(cityMapper.toDTO(weather.getCity()));
        weatherDto.setType(weatherTypeMapper.toDTO(weather.getType()));
        weatherDto.setTime(weather.getTime());
        weatherDto.setDate(weather.getDate());
        weatherDto.setTemperature(weather.getTemperature());
        return weatherDto;

    }

    @Override
    public Weather toWeather(WeatherDto weatherDto) {
        if (weatherDto == null) return null;
        Weather weather = new Weather();
        City city = cityMapper.toCity(weatherDto.getCity());
        WeatherType weatherType = weatherTypeMapper.toWeatherType(weatherDto.getType());
        weather.setId(weatherDto.getId());
        weather.setCity(city);
        weather.setTime(weatherDto.getTime());
        weather.setTemperature(weatherDto.getTemperature());
        weather.setDate(weatherDto.getDate());
        weather.setType(weatherType);
        return weather;
    }
}
