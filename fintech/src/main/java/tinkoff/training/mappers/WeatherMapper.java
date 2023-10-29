package tinkoff.training.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherDto;
public interface WeatherMapper {
    WeatherDto toDTO(Weather weather);
    Weather toWeather(WeatherDto weatherDto);
}
