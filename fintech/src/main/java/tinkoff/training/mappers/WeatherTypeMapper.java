package tinkoff.training.mappers;

import org.mapstruct.Mapper;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.models.WeatherDto;
import tinkoff.training.models.WeatherTypeDto;

@Mapper
public interface WeatherTypeMapper {
    WeatherTypeDto toDTO(WeatherType weatherTypeDto);
    WeatherType toWeatherType(WeatherTypeDto WeatherT);
}
