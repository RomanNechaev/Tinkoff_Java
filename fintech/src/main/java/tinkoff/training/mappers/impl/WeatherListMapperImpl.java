package tinkoff.training.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.Weather;
import tinkoff.training.mappers.WeatherListMapper;
import tinkoff.training.mappers.WeatherMapper;
import tinkoff.training.models.WeatherDto;

import java.util.List;
@Component
public class WeatherListMapperImpl implements WeatherListMapper {
    private final WeatherMapper weatherMapper;
    @Autowired
    public WeatherListMapperImpl(WeatherMapper weatherMapper){
        this.weatherMapper = weatherMapper;
    }
    @Override
    public List<Weather> toWeatherList(List<WeatherDto> dtos) {
        if(dtos==null) return null;
        return dtos.stream().map(weatherMapper::toWeather).toList();
    }

    @Override
    public List<WeatherDto> toDTOList(List<Weather> models) {
        if(models==null) return null;
        return models.stream().map(weatherMapper::toDTO).toList();
    }
}
