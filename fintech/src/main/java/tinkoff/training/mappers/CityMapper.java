package tinkoff.training.mappers;

import org.mapstruct.Mapper;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.CityDto;
import tinkoff.training.models.WeatherDto;

@Mapper
public interface CityMapper {
    CityDto toDTO(City city);
    City toCity(CityDto cityDto);
}
