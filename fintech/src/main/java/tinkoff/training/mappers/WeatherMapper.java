package tinkoff.training.mappers;

import org.mapstruct.Mapper;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherDto;

@Mapper
public interface WeatherMapper {
    WeatherDto toDTO(Weather weather);
    Weather toWeather(WeatherDto weatherDto);
}
