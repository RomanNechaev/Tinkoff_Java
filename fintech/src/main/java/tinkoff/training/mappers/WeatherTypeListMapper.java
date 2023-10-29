package tinkoff.training.mappers;

import org.mapstruct.Mapper;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.models.WeatherTypeDto;

import java.util.List;

@Mapper(uses = WeatherTypeListMapper.class)
public interface WeatherTypeListMapper {
    List<WeatherType> toWeatherList(List<WeatherTypeDto> dtos);

    List<WeatherTypeDto> toDTOList(List<WeatherType> models);
}
