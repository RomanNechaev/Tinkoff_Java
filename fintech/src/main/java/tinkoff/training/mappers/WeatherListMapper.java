package tinkoff.training.mappers;

import org.mapstruct.Mapper;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherDto;

import java.util.List;

@Mapper(uses = WeatherMapper.class)
public interface WeatherListMapper {
    List<Weather> toWeatherList(List<WeatherDto> dtos);
    List<WeatherDto> toDTOList(List<Weather> models);
}
