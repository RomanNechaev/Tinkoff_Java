package tinkoff.training.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.City;
import tinkoff.training.mappers.CityListMapper;
import tinkoff.training.mappers.CityMapper;
import tinkoff.training.models.CityDto;

import java.util.List;

@Component
public class CityListMapperImpl implements CityListMapper {
    private final CityMapper cityMapper;

    @Autowired
    public CityListMapperImpl(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public List<City> toCityList(List<CityDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(cityMapper::toCity).toList();
    }

    @Override
    public List<CityDto> toDTOList(List<City> models) {
        if (models == null) return null;
        return models.stream().map(cityMapper::toDTO).toList();
    }
}
