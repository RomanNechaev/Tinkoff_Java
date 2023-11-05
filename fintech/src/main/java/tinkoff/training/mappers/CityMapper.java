package tinkoff.training.mappers;

import tinkoff.training.entities.City;
import tinkoff.training.models.CityDto;

public interface CityMapper {
    CityDto toDTO(City city);

    City toCity(CityDto cityDto);
}
