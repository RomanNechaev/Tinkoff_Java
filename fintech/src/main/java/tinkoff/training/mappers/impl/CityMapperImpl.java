package tinkoff.training.mappers.impl;

import org.springframework.stereotype.Component;
import tinkoff.training.entities.City;
import tinkoff.training.mappers.CityMapper;
import tinkoff.training.models.CityDto;

@Component
public class CityMapperImpl implements CityMapper {

    @Override
    public CityDto toDTO(City city) {
        if (city == null) {
            return null;
        }
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        return cityDto;
    }

    @Override
    public City toCity(CityDto cityDto) {
        if (cityDto == null) {
            return null;
        }
        City city = new City();
        city.setId(cityDto.getId());
        city.setName(cityDto.getName());
        return city;
    }
}
