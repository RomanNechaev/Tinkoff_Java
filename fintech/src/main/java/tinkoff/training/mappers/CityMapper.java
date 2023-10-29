package tinkoff.training.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.CityDto;
import tinkoff.training.models.WeatherDto;
public interface CityMapper {
    CityDto toDTO(City city);
    City toCity(CityDto cityDto);
}
