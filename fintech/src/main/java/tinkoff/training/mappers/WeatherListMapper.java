package tinkoff.training.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherDto;

import java.util.List;
public interface WeatherListMapper {
    List<Weather> toWeatherList(List<WeatherDto> dtos);
    List<WeatherDto> toDTOList(List<Weather> models);
}
