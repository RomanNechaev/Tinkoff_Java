package tinkoff.training.mappers;

import tinkoff.training.entities.WeatherType;
import tinkoff.training.models.WeatherTypeDto;

import java.util.List;

public interface WeatherTypeListMapper {
    List<WeatherType> toWeatherList(List<WeatherTypeDto> dtos);

    List<WeatherTypeDto> toDTOList(List<WeatherType> models);
}
