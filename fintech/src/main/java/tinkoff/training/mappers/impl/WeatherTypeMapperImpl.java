package tinkoff.training.mappers.impl;

import org.springframework.stereotype.Component;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.mappers.WeatherTypeMapper;
import tinkoff.training.models.WeatherTypeDto;
@Component
public class WeatherTypeMapperImpl implements WeatherTypeMapper {
    @Override
    public WeatherTypeDto toDTO(WeatherType weatherType) {
        if(weatherType==null) return null;
        WeatherTypeDto weatherTypeDto = new WeatherTypeDto();
        weatherTypeDto.setId(weatherType.getId());
        weatherTypeDto.setType(weatherType.getType());
        return weatherTypeDto;
    }

    @Override
    public WeatherType toWeatherType(WeatherTypeDto weatherTypeDto) {
        if(weatherTypeDto==null) return null;
        WeatherType weatherType = new WeatherType();
        weatherType.setId(weatherTypeDto.getId());
        weatherType.setType(weatherTypeDto.getType());
        return weatherType;
    }
}
