package tinkoff.training.mappers;

import tinkoff.training.entities.WeatherType;
import tinkoff.training.models.WeatherTypeDto;

public interface WeatherTypeMapper {
    WeatherTypeDto toDTO(WeatherType weatherTypeDto);

    WeatherType toWeatherType(WeatherTypeDto WeatherT);
}
