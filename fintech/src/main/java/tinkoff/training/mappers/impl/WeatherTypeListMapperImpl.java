package tinkoff.training.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.mappers.WeatherTypeListMapper;
import tinkoff.training.mappers.WeatherTypeMapper;
import tinkoff.training.models.WeatherTypeDto;

import java.util.List;

@Component
public class WeatherTypeListMapperImpl implements WeatherTypeListMapper {
    private final WeatherTypeMapper weatherTypeMapper;

    @Autowired
    public WeatherTypeListMapperImpl(WeatherTypeMapper weatherTypeMapper) {
        this.weatherTypeMapper = weatherTypeMapper;
    }

    @Override
    public List<WeatherType> toWeatherList(List<WeatherTypeDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(weatherTypeMapper::toWeatherType).toList();
    }

    @Override
    public List<WeatherTypeDto> toDTOList(List<WeatherType> models) {
        if (models == null) return null;
        return models.stream().map(weatherTypeMapper::toDTO).toList();
    }
}
